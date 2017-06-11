package com.sample.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.data.model.ValidDeal;
import com.sample.data.repository.ValidDealRepository;

/**
 * This class is designed for test data layer.
 * 
 * @author shyam.pareek
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class ValidDealRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private ValidDealRepository validDealRepository;

	/**
	 * Method is used to test save valid deals.
	 */
	@Test
	public void testSaveValid() {

		ValidDeal validDeal1 = validDealRepository
				.save(new ValidDeal(100l, "USD", "INR", 100.2f, new Date(), "test.csv"));

		assertEquals(1, validDealRepository.count());
		assertEquals(1, validDeal1.getId().longValue());

		ValidDeal validDeal2 = createValidDeal();
		validDealRepository.save(validDeal2);

		assertEquals(2, validDealRepository.count());
		assertEquals(2, validDeal2.getId().longValue());
		assertEquals(200, validDeal2.getDealId().longValue());
		assertEquals("USD", validDeal2.getFromCurrencyISOCode());
		assertEquals("INR", validDeal2.getToCurrencyISOCode());
		assertEquals(200, validDeal2.getFromCurrencyISOCodeAmount().longValue());
		assertEquals("test.csv", validDeal2.getFileName());

	}

	/**
	 * Create Valid Deal obj.
	 * 
	 * @return
	 */
	private ValidDeal createValidDeal() {
		ValidDeal validDeal = new ValidDeal();
		validDeal.setDealId(200l);
		validDeal.setFromCurrencyISOCode("USD");
		validDeal.setToCurrencyISOCode("INR");
		validDeal.setFromCurrencyISOCodeAmount(200f);
		validDeal.setDealTimeStamp(new Date());
		validDeal.setFileName("test.csv");
		return validDeal;

	}
}
