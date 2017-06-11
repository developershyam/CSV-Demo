package com.sample.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.data.model.AccumulateOrderingCurrency;

/**
 * This repository is designed for accumulate count for ordering currency.
 * 
 * @author shyam.pareek
 * 
 */
@Repository
public interface AccumulateOrderingCurrencyRepository extends
		JpaRepository<AccumulateOrderingCurrency, String> {

}
