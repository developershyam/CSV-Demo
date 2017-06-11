package com.sample.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * This class is used for common or utility application methods.
 * 
 * @author shyam.pareek
 * 
 */
@Component
public class AppUtils {

	/**
	 * Used for check string object should have non empty string If object is
	 * null or empty it gives you false otherwise returns true
	 * 
	 * @param value
	 * @return condition
	 */
	public static boolean hasValue(String value) {
		return StringUtils.hasText(value);
	}

	/**
	 * Used for check collection should have non empty. If collection is null or
	 * empty it gives you false otherwise returns true
	 * 
	 * @param value
	 * @return condition
	 */
	public static boolean hasValue(Collection<?> value) {
		boolean flag = false;
		if (value != null && value.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Used for check all elements of array should have non empty.
	 * 
	 * @param value
	 * @return condition
	 */
	public static boolean hasAllValue(String[] values) {
		if (values == null || values.length == 0)
			return false;
		for (int i = 0; i < values.length; i++) {
			if (!AppUtils.hasValue(values[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Create list in chunks/partitions
	 * 
	 * @param ls
	 * @param iParts
	 * @return
	 */
	public static <T> List<List<T>> chopIntoParts(final List<T> ls, final int iParts) {
		final List<List<T>> lsParts = new ArrayList<List<T>>();
		final int iChunkSize = ls.size() / iParts;
		int iLeftOver = ls.size() % iParts;
		int iTake = iChunkSize;

		for (int i = 0, iT = ls.size(); i < iT; i += iTake) {
			if (iLeftOver > 0) {
				iLeftOver--;

				iTake = iChunkSize + 1;
			} else {
				iTake = iChunkSize;
			}

			lsParts.add(new ArrayList<T>(ls.subList(i, Math.min(iT, i + iTake))));
		}

		return lsParts;
	}

	public static int getMaxChunkParts(int total, int maxPerChunk) {
		return ((total % maxPerChunk == 0) ? total / maxPerChunk : total / maxPerChunk + 1);
	}

	public static String[] arraySizeIncrease(String[] arr, int size) {
		if (arr == null || size == 0 || arr.length >= size) {
			return arr;
		}
		String emptyArr[] = new String[size - arr.length];
		String[] both = (String[]) ArrayUtils.addAll(arr, emptyArr);
		return both;
	}

	public static boolean isAllDigits(String val) {
		if (val == null || val.trim().equals(""))
			return false;
		if (val.trim().matches("\\d*")) {
			return true;
		}
		return false;
	}

	public static boolean isDecimal(String val) {
		if (val == null || val.trim().equals(""))
			return false;
		if (val.trim().matches("^[1-9]\\d*(\\.\\d+)?$")) {
			return true;
		}
		return false;
	}

	public static boolean isValidDate(String date) {
		if (date == null || date.trim().equals(""))
			return false;
		Pattern p = Pattern.compile("^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,3}$");
		return p.matcher(date.trim()).matches();
	}

	public static Date getDate(String date) {
		if (date == null || date.trim().equals(""))
			return null;
		try {
			return AppConstant.SIMPLE_DATE_FORMAT.parse(date.trim());
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getFileNameExtension(String fileName) {
		if (!AppUtils.hasValue(fileName))
			return "";
		try {
			return fileName.substring(0, fileName.lastIndexOf("."));
		} catch (Exception e) {
			return "";
		}
	}

	public static String getFileNamePart(String fileName) {
		if (!AppUtils.hasValue(fileName))
			return "";
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (Exception e) {
			return "";
		}
	}
}
