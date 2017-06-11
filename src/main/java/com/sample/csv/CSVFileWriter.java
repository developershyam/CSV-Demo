package com.sample.csv;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Date;

import com.sample.data.model.ValidDeal;
import com.sample.util.AppConstant;
import com.sample.util.AppUtils;

/**
 * Class is design to generate CSV sample file.
 * 
 * @author shyam.pareek
 *
 */
public class CSVFileWriter {

	private boolean temFile = false;

	public CSVFileWriter(boolean temFile) {
		this.temFile = temFile;
	}

	public byte[] writeCsvFile(String fileName, int maxRow, int maxInvalidRow) {

		long start = System.currentTimeMillis();
		FileWriter fileWriter = null;
		File file = null;
		byte[] array = null;
		try {

			if (temFile) {
				file = File.createTempFile(AppUtils.getFileNamePart(fileName), AppUtils.getFileNamePart(fileName));
				file.deleteOnExit();
			} else {
				file = new File(fileName);

			}

			fileWriter = new FileWriter(file);
			// Write the CSV file header
			fileWriter.append(AppConstant.FILE_HEADER.toString());
			fileWriter.append(AppConstant.NEW_LINE_SEPARATOR);

			int maxValidChunk = maxRow / maxInvalidRow;
			for (int i = 0; i < maxRow; i++) {

				Long dealId = i + 1l;
				String fromISO = "USD";
				if (dealId % (maxValidChunk + 1) == 0) {
					fromISO = "AED";
				}
				String toISO = "INR";
				if (dealId % maxValidChunk == 0) {
					toISO = "";
				}

				ValidDeal validDeal = new ValidDeal(1000 + dealId, fromISO, toISO, 100.1f + dealId, new Date(),
						fileName);

				fileWriter.append(String.valueOf(validDeal.getDealId()) + AppConstant.COMMA_DELIMITER);
				fileWriter.append(validDeal.getFromCurrencyISOCode() + AppConstant.COMMA_DELIMITER);
				fileWriter.append(validDeal.getToCurrencyISOCode() + AppConstant.COMMA_DELIMITER);
				fileWriter.append(validDeal.getFromCurrencyISOCodeAmount() + AppConstant.COMMA_DELIMITER);
				fileWriter.append(String.valueOf(AppConstant.SIMPLE_DATE_FORMAT.format(validDeal.getDealTimeStamp()))
						+ AppConstant.NEW_LINE_SEPARATOR);

			}

			long end = System.currentTimeMillis();

			System.out.println(
					"CSV file '" + file.getAbsolutePath() + "' was created successfully !!! Time: " + (end - start));

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
				array = Files.readAllBytes(file.toPath());
			} catch (Exception e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
		return array;
	}

	public static void main(String[] args) {

		CSVFileWriter csvFileWriter = new CSVFileWriter(false);

		String fileName = null;
		if (args.length > 0) {
			fileName = args[0];
		} else {
			fileName = System.getProperty("user.home") + "/test-data.csv";
		}

		csvFileWriter.writeCsvFile(fileName, 100000, 500);
	}
}
