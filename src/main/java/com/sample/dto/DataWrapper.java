package com.sample.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class is designed for data with pagination.
 * 
 * @author shyam.pareek
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataWrapper implements Serializable {

	private static final long serialVersionUID = 9928712342L;

	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalElement;
	private List<?> data;

	public List<?> getData() {
		return data;
	}
	
	public void setData(List<?> data) {
		this.data = data;
	}
	
   public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}	

}
