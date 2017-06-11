package com.sample.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sample.data.model.ValidDeal;

/**
 * This repository is designed for ValidDeals.
 * 
 * @author shyam.pareek
 * 
 */
@Repository
public interface ValidDealRepository extends JpaRepository<ValidDeal, Long> {

	@Query("SELECT COUNT(vd) FROM ValidDeal vd WHERE vd.fileName=?1")
	int getCountByFileName(String fileName);

	@Query("SELECT vd from ValidDeal vd WHERE vd.fileName=?1")
	Page<ValidDeal> getValidDeals(String fileName, Pageable pageable);

	@Modifying
	@Query("DELETE FROM ValidDeal vd WHERE vd.fileName=?1")
	void deleteByFile(String fileName);

	@Query("SELECT vd.fromCurrencyISOCode as isoCode, COUNT(vd) as totalCount FROM ValidDeal vd WHERE vd.fileName=?1 GROUP BY vd.fromCurrencyISOCode ORDER BY vd.fromCurrencyISOCode ASC")
	List<Object[]> getCountISOByFile(String fileName);
}
