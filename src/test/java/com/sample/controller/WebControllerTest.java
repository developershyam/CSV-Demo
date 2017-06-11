package com.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * This class is designed for test end points of web controller.
 * 
 * @author shyam.pareek
 *
 */
@RunWith(SpringRunner.class)
public class WebControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	WebController webController;

	/**
	 * Setup configuration method.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(webController).build();
	}

	/**
	 * Method is used to check view of home/welcome page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN).contentType(MediaType.TEXT_PLAIN)).andDo(print())
				.andExpect(status().isOk()).andExpect(view().name("welcome"));
	}

	/**
	 * Method is used to check view of error page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testErrorPage() throws Exception {
		this.mockMvc.perform(get("/error").accept(MediaType.TEXT_PLAIN).contentType(MediaType.TEXT_PLAIN))
				.andDo(print()).andExpect(status().isOk()).andExpect(view().name("errorPage"));
	}
}
