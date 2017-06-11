package com.sample.service;

import org.springframework.data.domain.Pageable;

import com.sample.dto.CSVFileInfo;
import com.sample.dto.DataWrapper;

/**
 * This interface is used for design employee business.
 * 
 * @author shyam.pareek
 * 
 */
public interface CSVDealService {

	void saveCSVData(String fileName, byte[] bytes);
	
	boolean cvsFileExist(String fileName);

	CSVFileInfo getCSVFileInfo(String fileName);

	DataWrapper getValidDeals(String fileName, Pageable pageable);

	DataWrapper getInvalidDeals(String fileName, Pageable pageable);

	void deleteByFile(String fileName);

	void deleteAll();

	DataWrapper getAccumulateOrderingCurrency(Pageable pageable);

}
