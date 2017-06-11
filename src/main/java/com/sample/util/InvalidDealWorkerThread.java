package com.sample.util;

import java.util.List;

import com.sample.data.model.InvalidDeal;
import com.sample.data.repository.InvalidDealRepository;

/**
 * Invalid Deal thread for executing invalid deals in parallel process with
 * chunk values.
 * 
 * @author shyam.pareek
 * 
 */
public class InvalidDealWorkerThread implements Runnable {

	private int threadId;
	private InvalidDealRepository invalidDealRepository;
	private List<InvalidDeal> invalidDeals;

	public InvalidDealWorkerThread(int threadId, InvalidDealRepository invalidDealRepository,
			List<InvalidDeal> invalidDeals) {
		this.threadId = threadId;
		this.invalidDealRepository = invalidDealRepository;
		this.invalidDeals = invalidDeals;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		processCommand();
		long end = System.currentTimeMillis();
		System.out.println(" [Thread Invalid Deal - " + threadId + "]  Time : " + ((end - start) / 1000)
				+ " sec, Invalid Deal count: " + invalidDeals.size());
	}

	private void processCommand() {
		invalidDealRepository.save(invalidDeals);
		invalidDealRepository.flush();
	}

}
