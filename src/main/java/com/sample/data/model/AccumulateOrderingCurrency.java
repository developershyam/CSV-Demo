package com.sample.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This model is designed for ValidDeals.
 * 
 * @author shyam.pareek
 * 
 */
@Entity
@Table(name = "accumulate_ordering_currency")
public class AccumulateOrderingCurrency implements Serializable {

	private static final long serialVersionUID = 9928712342L;

	@Id
	@Column(name = "from_currency_iso_code")
	private String fromCurrencyISOCode;

	@Column(name = "totalCount")
	private Long totalCount;

	public AccumulateOrderingCurrency() {
		// TODO Auto-generated constructor stub
	}

	public AccumulateOrderingCurrency(String fromCurrencyISOCode,
			Long totalCount) {
		this.fromCurrencyISOCode = fromCurrencyISOCode;
		this.totalCount = totalCount;
	}

	public String getFromCurrencyISOCode() {
		return fromCurrencyISOCode;
	}

	public void setFromCurrencyISOCode(String fromCurrencyISOCode) {
		this.fromCurrencyISOCode = fromCurrencyISOCode;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
