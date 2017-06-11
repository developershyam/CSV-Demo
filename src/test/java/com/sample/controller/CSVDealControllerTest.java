package com.sample.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sample.dto.CSVFileInfo;
import com.sample.dto.DataWrapper;
import com.sample.service.CSVDealService;
import com.sample.util.AppConstant;

/**
 * This class is designed for test end points of CSV controller.
 * 
 * @author shyam.pareek
 *
 */
@RunWith(SpringRunner.class)
public class CSVDealControllerTest {

	private MockMvc mockMvc;

	@Mock
	CSVDealService csvDealService;

	@InjectMocks
	CSVDealController csvDealController;

	/**
	 * Setup configuration method.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(csvDealController).build();
	}

	/**
	 * Method is used to check view of file upload.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFileUpload() throws Exception {
		this.mockMvc.perform(get("/file/upload").accept(MediaType.TEXT_PLAIN).contentType(MediaType.TEXT_PLAIN))
				.andDo(print()).andExpect(status().isOk()).andExpect(view().name("fileUpload"));
	}

	/**
	 * Method is used to check save/upload CSV file.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFileUploadSave() throws Exception {

		String fileName = "test-data.csv";
		byte bytes[] = "1,USD,INR,100,2017-06-10 16:00:00.000".getBytes();

		when(csvDealService.cvsFileExist(fileName)).thenReturn(false);

		MockMultipartFile file = new MockMultipartFile("file", fileName, null, bytes);
		this.mockMvc.perform(fileUpload("/file/upload").file(file)).andDo(print())
				.andExpect(redirectedUrl("/file/upload")).andExpect(flash().attributeExists("message"))
				.andExpect(flash().attribute("message", Matchers.containsString("You successfully uploaded")));

		verify(csvDealService).cvsFileExist(fileName);
	}

	/**
	 * Method is used to check save/upload CSV file, If file already exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFileUploadFileExist() throws Exception {

		String fileName = "test-data.csv";
		byte bytes[] = "1,USD,INR,100,2017-06-10 16:00:00.000".getBytes();

		when(csvDealService.cvsFileExist(fileName)).thenReturn(true);

		MockMultipartFile file = new MockMultipartFile("file", fileName, null, bytes);
		this.mockMvc.perform(fileUpload("/file/upload").file(file)).andDo(print())
				.andExpect(redirectedUrl("/file/upload")).andExpect(flash().attributeExists("message"))
				.andExpect(flash().attribute("message", String.format(AppConstant.MSG_FILE_EXIST, fileName)));

		verify(csvDealService).cvsFileExist(fileName);
	}

	/**
	 * Method is used to check save/upload CSV file, If file is empty.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFileUploadEmptyData() throws Exception {

		String fileName = "test-data.csv";
		byte bytes[] = "".getBytes();

		MockMultipartFile file = new MockMultipartFile("file", fileName, null, bytes);
		this.mockMvc.perform(fileUpload("/file/upload").file(file)).andDo(print())
				.andExpect(redirectedUrl("/file/upload")).andExpect(flash().attributeExists("message"))
				.andExpect(flash().attribute("message", AppConstant.MSG_SELECT_FILE));

	}

	/**
	 * Method is used to check file search view.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFileSearch() throws Exception {

		this.mockMvc.perform(get("/file/search").accept(MediaType.TEXT_PLAIN).contentType(MediaType.TEXT_PLAIN))
				.andDo(print()).andExpect(status().isOk()).andExpect(view().name("fileSearch"));
	}

	/**
	 * Method is used to get CSV file info.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCSVFileInfo() throws Exception {
		String fileName = "test-data.csv";

		when(csvDealService.getCSVFileInfo(fileName)).thenReturn(new CSVFileInfo(fileName, 10, 2));

		this.mockMvc
				.perform(get("/getCSVFileInfo?fileName=" + fileName).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.httpStatus").value(200))
				.andExpect(jsonPath("$.data.fileName").value(fileName));

		verify(csvDealService).getCSVFileInfo(fileName);
	}

	/**
	 * Method is used to get Valid Deals of CSV file.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCSVFileValidDeals() throws Exception {
		String fileName = "test-data.csv";

		PageRequest pageRequest = new PageRequest(0, 5);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageSize(5);
		dataWrapper.setTotalElement(10);
		when(csvDealService.getValidDeals(fileName, pageRequest)).thenReturn(dataWrapper);

		this.mockMvc
				.perform(get("/getCSVFileValidDeals?fileName=" + fileName + "&currentPage=0&pageSize=5")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.totalElement").value(10))
				.andExpect(jsonPath("$.pageSize").value(5));

		verify(csvDealService).getValidDeals(fileName, pageRequest);
	}

	/**
	 * Method is used to get Invalid Deals of CSV file.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCSVFileInvalidDeals() throws Exception {
		String fileName = "test-data.csv";

		PageRequest pageRequest = new PageRequest(0, 5);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageSize(5);
		dataWrapper.setTotalElement(10);
		when(csvDealService.getInvalidDeals(fileName, pageRequest)).thenReturn(dataWrapper);

		this.mockMvc
				.perform(get("/getCSVFileInvalidDeals?fileName=" + fileName + "&currentPage=0&pageSize=5")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.totalElement").value(10))
				.andExpect(jsonPath("$.pageSize").value(5));

		verify(csvDealService).getInvalidDeals(fileName, pageRequest);
	}

	/**
	 * Method is used to get accumulative count of ordering currency.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAccumulateOrderingCurrency() throws Exception {

		PageRequest pageRequest = new PageRequest(0, 5);
		DataWrapper dataWrapper = new DataWrapper();
		dataWrapper.setPageSize(5);
		dataWrapper.setTotalElement(10);
		when(csvDealService.getAccumulateOrderingCurrency(pageRequest)).thenReturn(dataWrapper);

		this.mockMvc
				.perform(get("/getAccumulateOrderingCurrency?currentPage=0&pageSize=5")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.totalElement").value(10))
				.andExpect(jsonPath("$.pageSize").value(5));

		verify(csvDealService).getAccumulateOrderingCurrency(pageRequest);
	}

	/**
	 * Method is used to delete file by name.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteByFile() throws Exception {
		String fileName = "test-data.csv";
		this.mockMvc
				.perform(delete("/deleteByFile?fileName=" + fileName).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.httpStatus").value(200));

	}

	/**
	 * Method is used to delete all CSV data.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testDeleteAll() throws Exception {
		this.mockMvc
				.perform(
						delete("/deleteAll").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.httpStatus").value(200));

	}
}
