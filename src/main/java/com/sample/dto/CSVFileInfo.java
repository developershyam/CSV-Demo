package com.sample.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class is designed for CSV file info.
 * 
 * @author shyam.pareek
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CSVFileInfo implements Serializable {

	private static final long serialVersionUID = 9928712342L;

	private String fileName;
	private int validDealCount;
	private int invalidDealCount;

	public CSVFileInfo() {
	}

	public CSVFileInfo(String fileName, int validDealCount, int invalidDealCount) {
		this.fileName = fileName;
		this.validDealCount = validDealCount;
		this.invalidDealCount = invalidDealCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getValidDealCount() {
		return validDealCount;
	}

	public void setValidDealCount(int validDealCount) {
		this.validDealCount = validDealCount;
	}

	public int getInvalidDealCount() {
		return invalidDealCount;
	}

	public void setInvalidDealCount(int invalidDealCount) {
		this.invalidDealCount = invalidDealCount;
	}

}
