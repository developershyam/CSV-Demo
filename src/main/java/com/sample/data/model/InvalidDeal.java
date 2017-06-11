package com.sample.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sample.util.AppConstant;

/**
 * This model is designed for InvalidDeals.
 * 
 * @author shyam.pareek
 * 
 */
@Entity
@Table(name = "invalid_deal")
public class InvalidDeal implements Serializable {

	private static final long serialVersionUID = 9928712342L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "deal_id")
	private Long dealId;

	@Column(name = "from_currency_iso_code")
	private String fromCurrencyISOCode;

	@Column(name = "to_currency_iso_code")
	private String toCurrencyISOCode;

	@Column(name = "from_currency_iso_code_amount")
	private Float fromCurrencyISOCodeAmount;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstant.DATE_FORMAT)
	@Column(name = "deal_time_stamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dealTimeStamp;

	@Column(name = "file_name")
	private String fileName;

	public InvalidDeal() {
		// TODO Auto-generated constructor stub
	}

	public InvalidDeal(Long dealId, String fromCurrencyISOCode, String toCurrencyISOCode,
			Float fromCurrencyISOCodeAmount, Date dealTimeStamp, String fileName) {
		this.dealId = dealId;
		this.fromCurrencyISOCode = fromCurrencyISOCode;
		this.toCurrencyISOCode = toCurrencyISOCode;
		this.fromCurrencyISOCodeAmount = fromCurrencyISOCodeAmount;
		this.dealTimeStamp = dealTimeStamp;
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}

	public String getFromCurrencyISOCode() {
		return fromCurrencyISOCode;
	}

	public void setFromCurrencyISOCode(String fromCurrencyISOCode) {
		this.fromCurrencyISOCode = fromCurrencyISOCode;
	}

	public String getToCurrencyISOCode() {
		return toCurrencyISOCode;
	}

	public void setToCurrencyISOCode(String toCurrencyISOCode) {
		this.toCurrencyISOCode = toCurrencyISOCode;
	}

	public Float getFromCurrencyISOCodeAmount() {
		return fromCurrencyISOCodeAmount;
	}

	public void setFromCurrencyISOCodeAmount(Float fromCurrencyISOCodeAmount) {
		this.fromCurrencyISOCodeAmount = fromCurrencyISOCodeAmount;
	}

	public Date getDealTimeStamp() {
		return dealTimeStamp;
	}

	public void setDealTimeStamp(Date dealTimeStamp) {
		this.dealTimeStamp = dealTimeStamp;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
