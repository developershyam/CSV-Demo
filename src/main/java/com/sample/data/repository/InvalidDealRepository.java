package com.sample.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sample.data.model.InvalidDeal;

/**
 * This repository is designed for InvalidDeals.
 * 
 * @author shyam.pareek
 * 
 */
@Repository
public interface InvalidDealRepository extends JpaRepository<InvalidDeal, Long> {

	@Query("SELECT COUNT(ivd) FROM InvalidDeal ivd WHERE ivd.fileName=?1")
	int getCountByFileName(String fileName);

	@Query("SELECT ivd from InvalidDeal ivd WHERE ivd.fileName=?1")
	Page<InvalidDeal> getInvalidDeals(String fileName, Pageable pageable);

	@Modifying
	@Query("DELETE FROM InvalidDeal ivd WHERE ivd.fileName=?1")
	void deleteByFile(String fileName);

}
