package com.sample.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class is designed for same REST response for all end-points.
 * 
 * @author shyam.pareek
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse implements Serializable {

	private static final long serialVersionUID = 9928712342l;

	private int httpStatus;
	private String message;
	private boolean processed;
	private Object data;

	public AppResponse() {
		// TODO Auto-generated constructor stub
	}

	public AppResponse(int httpStatus, boolean processed, String message, Object data) {

		this.httpStatus = httpStatus;
		this.processed = processed;
		this.message = message;
		this.data = data;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

}
