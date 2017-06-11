package com.sample.util;

import java.util.List;

import com.sample.data.model.ValidDeal;
import com.sample.data.repository.ValidDealRepository;

/**
 * Valid Deal thread for executing valid deals in parallel process with chunk
 * values.
 * 
 * @author shyam.pareek
 * 
 */
public class ValidDealWorkerThread implements Runnable {

	private int threadId;
	private ValidDealRepository validDealRepository;
	private List<ValidDeal> validDeals;

	public ValidDealWorkerThread(int threadId, ValidDealRepository validDealRepository, List<ValidDeal> validDeals) {
		this.threadId = threadId;
		this.validDealRepository = validDealRepository;
		this.validDeals = validDeals;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		processCommand();
		long end = System.currentTimeMillis();
		System.out.println(" [Thread Valid Deal - " + threadId + "] Time: " + ((end - start) / 1000)
				+ " sec, Valid Deal count: " + validDeals.size());
	}

	private void processCommand() {
		validDealRepository.save(validDeals);
		validDealRepository.flush();
	}

}
