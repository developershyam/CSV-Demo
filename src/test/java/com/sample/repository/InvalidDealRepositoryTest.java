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

import com.sample.data.model.InvalidDeal;
import com.sample.data.repository.InvalidDealRepository;

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
public class InvalidDealRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private InvalidDealRepository invalidDealRepository;

	/**
	 * Method is used to test save valid deals.
	 */
	@Test
	public void testSaveInvalid() {

		InvalidDeal invalidDeal1 = invalidDealRepository
				.save(new InvalidDeal(100l, "USD", "INR", 100.2f, new Date(), "test.csv"));

		assertEquals(1, invalidDealRepository.count());
		assertEquals(1, invalidDeal1.getId().longValue());

		InvalidDeal invalidDeal2 = createInvalidDeal();
		invalidDealRepository.save(invalidDeal2);

		assertEquals(2, invalidDealRepository.count());
		assertEquals(2, invalidDeal2.getId().longValue());
		assertEquals(200, invalidDeal2.getDealId().longValue());
		assertEquals("USD", invalidDeal2.getFromCurrencyISOCode());
		assertEquals("INR", invalidDeal2.getToCurrencyISOCode());
		assertEquals(200, invalidDeal2.getFromCurrencyISOCodeAmount().longValue());
		assertEquals("test.csv", invalidDeal2.getFileName());

	}

	/**
	 * Create Valid Deal obj.
	 * 
	 * @return
	 */
	private InvalidDeal createInvalidDeal() {
		InvalidDeal invalidDeal = new InvalidDeal();
		invalidDeal.setDealId(200l);
		invalidDeal.setFromCurrencyISOCode("USD");
		invalidDeal.setToCurrencyISOCode("INR");
		invalidDeal.setFromCurrencyISOCodeAmount(200f);
		invalidDeal.setDealTimeStamp(new Date());
		invalidDeal.setFileName("test.csv");
		return invalidDeal;

	}
}
