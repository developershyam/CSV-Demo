package com.sample.service;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.csv.CSVFileWriter;
import com.sample.data.repository.AccumulateOrderingCurrencyRepository;
import com.sample.data.repository.InvalidDealRepository;
import com.sample.data.repository.ValidDealRepository;
import com.sample.dto.CSVFileInfo;
import com.sample.dto.DataWrapper;

/**
 * This class is designed for test service layer.
 * 
 * @author shyam.pareek
 *
 */
@SpringBootTest
@ComponentScan(basePackages = "com.sample")
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class CSVDealServiceTest {
	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private ValidDealRepository validDealRepository;

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	@Autowired
	private AccumulateOrderingCurrencyRepository accumulateOrderingCurrencyRepository;

	@Autowired
	private CSVDealService csvDealService;

	private String fileName = "test-data.csv";

	/**
	 * Method is used to test save CSV data.
	 */
	@Test
	public void testSaveCSVData() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		assertEquals(8, validDealRepository.count());
		assertEquals(2, accumulateOrderingCurrencyRepository.count());
		assertEquals(2, invalidDealRepository.count());
	}

	/**
	 * Method is used to test file already exist
	 */
	@Test
	public void testCSVFileExistCSVData() {
		boolean flag = csvDealService.cvsFileExist(fileName);
		assertEquals(false, flag);

		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		flag = csvDealService.cvsFileExist(fileName);
		assertEquals(true, flag);

	}

	/**
	 * Method is used to test CSV file info.
	 */
	@Test
	public void testGetCSVFileInfo() {
		CSVFileInfo csvFileInfo = csvDealService.getCSVFileInfo(fileName);
		assertEquals(fileName, csvFileInfo.getFileName());

	}

	/**
	 * Method is used to test get valid deals.
	 */
	@Test
	public void testGetValidDeals() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		DataWrapper dataWrapper = csvDealService.getValidDeals(fileName, new PageRequest(0, 5));
		assertEquals(8, dataWrapper.getTotalElement());

	}

	/**
	 * Method is used to test get invalid deals.
	 */
	@Test
	public void testGetInvalidDeals() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		DataWrapper dataWrapper = csvDealService.getInvalidDeals(fileName, new PageRequest(0, 5));
		assertEquals(2, dataWrapper.getTotalElement());

	}

	/**
	 * Method is used to test get accumulative ordering currency.
	 */
	@Test
	public void testGetAccumulateOrderingCurrency() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		DataWrapper dataWrapper = csvDealService.getAccumulateOrderingCurrency(new PageRequest(0, 5));
		assertEquals(2, dataWrapper.getTotalElement());

	}

	/**
	 * Method is used to test delete CSV file.
	 */
	@Test
	public void testDeleteByFile() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		assertEquals(8, validDealRepository.count());
		assertEquals(2, invalidDealRepository.count());
		csvDealService.deleteByFile(fileName);
		assertEquals(0, validDealRepository.count());
		assertEquals(0, invalidDealRepository.count());
	}

	/**
	 * Method is used to test delete all CSV file.
	 */
	@Test
	public void testDeleteAll() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		assertEquals(8, validDealRepository.count());
		assertEquals(2, invalidDealRepository.count());
		csvDealService.deleteAll();
		assertEquals(0, validDealRepository.count());
		assertEquals(0, invalidDealRepository.count());
	}

	/**
	 * Method is used to test increase accumulative count if another file is
	 * added.
	 */
	@Test
	public void testIncreaseAccumulativeCurrency() {
		CSVFileWriter csvFileWriter = new CSVFileWriter(true);
		byte bytes[] = csvFileWriter.writeCsvFile(fileName, 10, 2);
		csvDealService.saveCSVData(fileName, bytes);
		assertEquals(8, validDealRepository.count());
		assertEquals(7, accumulateOrderingCurrencyRepository.findOne("USD").getTotalCount().longValue());
		assertEquals(2, invalidDealRepository.count());

		csvDealService.saveCSVData("test-data-2.csv", bytes);
		assertEquals(16, validDealRepository.count());
		assertEquals(14, accumulateOrderingCurrencyRepository.findOne("USD").getTotalCount().longValue());
		assertEquals(4, invalidDealRepository.count());
	}

	/**
	 * Method is used to test create and delete sample CSV file
	 */
	@Test
	public void testToCreateAndDeleteFile() throws Exception {
		File file = File.createTempFile("test-data", ".csv");
		file.deleteOnExit();
		CSVFileWriter.main(new String[] { file.getAbsolutePath() });
		assertEquals(true, file.exists());
	}
}
