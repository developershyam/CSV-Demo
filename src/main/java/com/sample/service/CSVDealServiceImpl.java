package com.sample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sample.data.model.AccumulateOrderingCurrency;
import com.sample.data.model.InvalidDeal;
import com.sample.data.model.ValidDeal;
import com.sample.data.repository.AccumulateOrderingCurrencyRepository;
import com.sample.data.repository.InvalidDealRepository;
import com.sample.data.repository.ValidDealRepository;
import com.sample.dto.CSVFileInfo;
import com.sample.dto.DataWrapper;
import com.sample.util.AppConstant;
import com.sample.util.AppUtils;
import com.sample.util.InvalidDealWorkerThread;
import com.sample.util.ValidDealWorkerThread;

/**
 * This class is used for write employee business.
 * 
 * @author shyam.pareek
 * 
 */
@Service
public class CSVDealServiceImpl implements CSVDealService {

	Logger logger = Logger.getLogger(CSVDealServiceImpl.class);

	@Autowired
	private ValidDealRepository validDealRepository;

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	@Autowired
	private AccumulateOrderingCurrencyRepository accumulateOrderingCurrencyRepository;

	@Override
	@Transactional(noRollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
	public void saveCSVData(String fileName, byte[] bytes) {

		List<ValidDeal> validDeals = new ArrayList<>();
		List<InvalidDeal> invalidDeals = new ArrayList<>();

		String completeData = new String(bytes);
		String[] rows = completeData.split("\\r?\\n");

		for (int i = 1; i < rows.length; i++) {
			String[] columns = rows[i].split(AppConstant.COMMA_DELIMITER);
			columns = AppUtils.arraySizeIncrease(columns, 5);
			boolean validDealId = AppUtils.isAllDigits(columns[AppConstant.DEAL_IDNX]);
			boolean validDate = AppUtils.isValidDate(columns[AppConstant.TIME_IDNX]);
			boolean validAmt = AppUtils.isDecimal(columns[AppConstant.FRM_ISO_AMT_IDNX]);

			if (columns != null && columns.length >= 5 && AppUtils.hasAllValue(columns) && validDealId && validDate
					&& validAmt) {
				ValidDeal validDeal = new ValidDeal(Long.parseLong(columns[AppConstant.DEAL_IDNX]),
						columns[AppConstant.FRM_ISO_IDNX], columns[AppConstant.TO_ISO_INDX],
						Float.parseFloat(columns[AppConstant.FRM_ISO_AMT_IDNX]),
						AppUtils.getDate(columns[AppConstant.TIME_IDNX]), fileName);
				validDeals.add(validDeal);
			} else {
				long dealId = validDealId ? Long.parseLong(columns[AppConstant.DEAL_IDNX]) : 0;
				Date timeStamp = validDate ? AppUtils.getDate(columns[AppConstant.TIME_IDNX]) : null;
				Float amount = validAmt ? Float.parseFloat(columns[AppConstant.FRM_ISO_AMT_IDNX]) : 0f;
				InvalidDeal invalidDeal = new InvalidDeal(dealId, columns[AppConstant.FRM_ISO_IDNX],
						columns[AppConstant.TO_ISO_INDX], amount, timeStamp, fileName);
				invalidDeals.add(invalidDeal);
			}
		}

		int validChunks = AppUtils.getMaxChunkParts(validDeals.size(), AppConstant.MAX_PER_CHUNK);
		int invalidChunks = AppUtils.getMaxChunkParts(invalidDeals.size(), AppConstant.MAX_PER_CHUNK);

		List<List<ValidDeal>> validDealChunks = AppUtils.chopIntoParts(validDeals, validChunks);
		List<List<InvalidDeal>> invalidDealChunks = AppUtils.chopIntoParts(invalidDeals, invalidChunks);

		ExecutorService executor = Executors.newFixedThreadPool(validChunks + invalidChunks);
		if (AppUtils.hasValue(validDealChunks)) {
			for (int i = 0; i < validChunks; i++) {
				Runnable worker = new ValidDealWorkerThread((i + 1), validDealRepository, validDealChunks.get(i));
				executor.execute(worker);
			}
		}

		if (AppUtils.hasValue(invalidDealChunks)) {
			for (int i = 0; i < invalidChunks; i++) {
				Runnable worker = new InvalidDealWorkerThread((i + 1), invalidDealRepository, invalidDealChunks.get(i));
				executor.execute(worker);
			}
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		System.out.println("Valid Deal count: " + validDeals.size() + ", Invalid Deal count: " + invalidDeals.size());
		increaseAccumulateCount(fileName);
	}

	private void increaseAccumulateCount(String fileName) {

		List<Object[]> results = validDealRepository.getCountISOByFile(fileName);
		if (AppUtils.hasValue(results)) {
			for (Object[] result : results) {
				String name = (String) result[0];
				Long count = ((Long) result[1]).longValue();
				if (accumulateOrderingCurrencyRepository.exists(name)) {
					AccumulateOrderingCurrency accumulateOrderingCurrency = accumulateOrderingCurrencyRepository
							.getOne(name);
					if (accumulateOrderingCurrency != null) {
						accumulateOrderingCurrency.setTotalCount(accumulateOrderingCurrency.getTotalCount() + count);
						accumulateOrderingCurrencyRepository.save(accumulateOrderingCurrency);
					}
				} else {
					AccumulateOrderingCurrency accumulateOrderingCurrency = new AccumulateOrderingCurrency(name, count);
					accumulateOrderingCurrencyRepository.save(accumulateOrderingCurrency);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean cvsFileExist(String fileName) {
		boolean existFile = false;
		int validCount = validDealRepository.getCountByFileName(fileName);
		int invalidCount = invalidDealRepository.getCountByFileName(fileName);
		if ((validCount + invalidCount) > 0) {
			existFile = true;
			logger.info("CSV file already exist.");
		}
		return existFile;
	}

	@Override
	@Transactional(readOnly = true)
	public CSVFileInfo getCSVFileInfo(String fileName) {
		int validCount = validDealRepository.getCountByFileName(fileName);
		int invalidCount = invalidDealRepository.getCountByFileName(fileName);
		CSVFileInfo csvFileInfo = new CSVFileInfo(fileName, validCount, invalidCount);
		return csvFileInfo;
	}

	@Override
	@Transactional(readOnly = true)
	public DataWrapper getValidDeals(String fileName, Pageable pageable) {

		Page<ValidDeal> validDealPage = validDealRepository.getValidDeals(fileName, pageable);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageNumber(validDealPage.getNumber() + 1);
		dataWrapper.setPageSize(validDealPage.getSize());
		dataWrapper.setTotalElement(validDealPage.getTotalElements());
		dataWrapper.setTotalPages(validDealPage.getTotalPages());

		List<ValidDeal> validDeals = validDealPage.getContent();
		dataWrapper.setData(validDeals);
		logger.info(String.format("CSVDealService#getValidDeals fileName: '%s', Total Count: %d ", fileName,
				dataWrapper.getTotalElement()));
		return dataWrapper;
	}

	@Override
	@Transactional(readOnly = true)
	public DataWrapper getInvalidDeals(String fileName, Pageable pageable) {

		Page<InvalidDeal> invalidDealPage = invalidDealRepository.getInvalidDeals(fileName, pageable);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageNumber(invalidDealPage.getNumber() + 1);
		dataWrapper.setPageSize(invalidDealPage.getSize());
		dataWrapper.setTotalElement(invalidDealPage.getTotalElements());
		dataWrapper.setTotalPages(invalidDealPage.getTotalPages());

		List<InvalidDeal> invalidDeals = invalidDealPage.getContent();
		dataWrapper.setData(invalidDeals);
		logger.info(String.format("CSVDealService#getInvalidDeals fileName: '%s', Total Count: %d ", fileName,
				dataWrapper.getTotalElement()));
		return dataWrapper;
	}

	@Override
	@Transactional(readOnly = true)
	public DataWrapper getAccumulateOrderingCurrency(Pageable pageable) {

		Page<AccumulateOrderingCurrency> accumulatePage = accumulateOrderingCurrencyRepository.findAll(pageable);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageNumber(accumulatePage.getNumber() + 1);
		dataWrapper.setPageSize(accumulatePage.getSize());
		dataWrapper.setTotalElement(accumulatePage.getTotalElements());
		dataWrapper.setTotalPages(accumulatePage.getTotalPages());

		List<AccumulateOrderingCurrency> accumulateOrderingCurrencies = accumulatePage.getContent();
		dataWrapper.setData(accumulateOrderingCurrencies);
		logger.info(String.format("CSVDealService#getAccumulateOrderingCurrency Total Count: %d ",
				dataWrapper.getTotalElement()));
		return dataWrapper;
	}

	@Override
	@Transactional
	public void deleteByFile(String fileName) {

		List<Object[]> results = validDealRepository.getCountISOByFile(fileName);
		if (AppUtils.hasValue(results)) {
			for (Object[] result : results) {
				String name = (String) result[0];
				Long count = ((Long) result[1]).longValue();
				if (accumulateOrderingCurrencyRepository.exists(name)) {
					AccumulateOrderingCurrency accumulateOrderingCurrency = accumulateOrderingCurrencyRepository
							.getOne(name);
					if (accumulateOrderingCurrency != null) {
						accumulateOrderingCurrency.setTotalCount(accumulateOrderingCurrency.getTotalCount() - count);
						accumulateOrderingCurrencyRepository.save(accumulateOrderingCurrency);
					}
				}
			}
		}
		validDealRepository.deleteByFile(fileName);
		invalidDealRepository.deleteByFile(fileName);
		logger.info(String.format("CSVDealService#deleteByFile File: %s deleted.", fileName));
	}

	@Override
	@Transactional
	public void deleteAll() {
		validDealRepository.deleteAll();
		invalidDealRepository.deleteAll();
		accumulateOrderingCurrencyRepository.deleteAll();
		logger.info("CSVDealService#deleteAll File: %s deleted all data.");
	}

}
