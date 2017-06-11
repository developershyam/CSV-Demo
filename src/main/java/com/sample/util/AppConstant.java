package com.sample.util;

import java.text.SimpleDateFormat;

/**
 * This interface is used for common application constant or property value.
 * 
 * @author shyam.pareek
 *
 */
public interface AppConstant {

	String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(DATE_FORMAT);
	
	int ERROR_CODE = 100;
	int SUCCESS_CODE = 200;
	
	String SUCCESS_MSG = "SUCCESS!!!";
	String ERROR_MSG = "ERROR!!!";

	int MAX_PER_CHUNK = 20000;

	// Delimiter used in CSV file
	String COMMA_DELIMITER = ",";
	String NEW_LINE_SEPARATOR = "\n";
	
	// CSV file header
	String FILE_HEADER = "DealId,FromCurrencyISOCode,ToCurrencyISOCode,FromCurrencyISOCodeAmount,DealTimeStamp";

	// CSV Attributes index
	int DEAL_IDNX = 0;
	int FRM_ISO_IDNX = 1;
	int TO_ISO_INDX = 2;
	int FRM_ISO_AMT_IDNX = 3;
	int TIME_IDNX = 4;

	String MSG_SUCCESS = "You successfully uploaded '%s' in %d Sec. <hr/> <div style=\"color:#B2FF59; background-color:black;padding: 10px;\"> Server Logs: <br/> %s </div>";
	String MSG_SELECT_FILE = "<span style=\"color:red\">Please select a file to upload or file should contains data.</span>";
	String MSG_FILE_EXIST = "<span style=\"color:red\">File '%s' already exist.</span>";

}
