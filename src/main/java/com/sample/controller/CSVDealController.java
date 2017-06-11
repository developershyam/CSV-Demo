package com.sample.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.dto.AppResponse;
import com.sample.dto.CSVFileInfo;
import com.sample.dto.DataWrapper;
import com.sample.service.CSVDealService;
import com.sample.util.AppConstant;
import com.sample.util.URLConstant;

/**
 * This class is design as controller to handle incoming request.
 * 
 * @author shyam.pareek
 * 
 */
@Controller
public class CSVDealController {

	Logger logger = Logger.getLogger(CSVDealController.class);
	@Autowired
	private CSVDealService csvDealService;

	@GetMapping(value = URLConstant.FILE_UPLOAD)
	public String fileUpload() {
		return "fileUpload";
	}

	@PostMapping(value = URLConstant.FILE_UPLOAD)
	public String fileUploadSubmit(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		String fileName = file.getOriginalFilename();
		logger.info("User submitted file for load into DB " + fileName);

		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		System.setOut(new java.io.PrintStream(out));

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", AppConstant.MSG_SELECT_FILE);
			logger.info(AppConstant.MSG_SELECT_FILE);
			return "redirect:" + URLConstant.FILE_UPLOAD;
		}
		if (csvDealService.cvsFileExist(fileName)) {
			redirectAttributes.addFlashAttribute("message", String.format(AppConstant.MSG_FILE_EXIST, fileName));
			logger.info(String.format(AppConstant.MSG_FILE_EXIST, fileName));
			return "redirect:" + URLConstant.FILE_UPLOAD;
		}

		try {

			byte[] bytes = file.getBytes();

			long start = System.currentTimeMillis();
			csvDealService.saveCSVData(fileName, bytes);
			long end = System.currentTimeMillis();
			long timeElapsed = (end - start) / 1000;

			System.out.println("Total time taken: " + timeElapsed + " sec.");
			String msg = String.format(AppConstant.MSG_SUCCESS, fileName, timeElapsed,
					out.toString().replaceAll("(\r\n|\n)", "<br />"));
			redirectAttributes.addFlashAttribute("message", msg);
			logger.info(String.format("File %s successfully loaded in DB. Time %d esc.", fileName, timeElapsed));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Exception: While file reading " + e.getMessage());
			logger.error("Exception: While file reading " + e.getMessage());
		}

		return "redirect:" + URLConstant.FILE_UPLOAD;
	}

	@GetMapping(value = URLConstant.FILE_SEARCH)
	public String fileSearch() {
		return "fileSearch";
	}

	@GetMapping(value = URLConstant.GET_CSV_FILE_INFO)
	public @ResponseBody AppResponse getCSVFileInfo(@RequestParam(required = true) String fileName) {

		CSVFileInfo csvFileInfo = csvDealService.getCSVFileInfo(fileName);
		if (csvFileInfo.getValidDealCount() + csvFileInfo.getInvalidDealCount() > 0) {
			return new AppResponse(AppConstant.SUCCESS_CODE, true, "SUCCESS", csvFileInfo);
		}
		return new AppResponse(AppConstant.ERROR_CODE, true, "SUCCESS", null);
	}

	@GetMapping(value = URLConstant.GET_CSV_FILE_VALIDDEALS)
	public @ResponseBody DataWrapper getCSVFileValidDeals(@RequestParam(required = true) String fileName,
			@RequestParam(required = false) int currentPage, @RequestParam(required = false) int pageSize) {
		int currentPageIndx = currentPage - 1;
		if (currentPageIndx < 0) {
			currentPageIndx = 0;
		}
		if (pageSize < 5)
			pageSize = 5;

		PageRequest pageRequest = new PageRequest(currentPageIndx, pageSize);
		DataWrapper dataWrapper = csvDealService.getValidDeals(fileName, pageRequest);
		logger.info(String.format("File search for ValidDeals %s with param currentPage=%d, pageSize=%d", fileName,
				currentPage, pageSize));
		return dataWrapper;
	}

	@GetMapping(value = URLConstant.GET_CSV_FILE_INVALIDDEALS)
	public @ResponseBody DataWrapper getCSVFileInvalidDeals(@RequestParam(required = true) String fileName,
			@RequestParam(required = false) int currentPage, @RequestParam(required = false) int pageSize) {
		int currentPageIndx = currentPage - 1;
		if (currentPageIndx < 0) {
			currentPageIndx = 0;
		}
		if (pageSize < 5)
			pageSize = 5;

		PageRequest pageRequest = new PageRequest(currentPageIndx, pageSize);
		DataWrapper dataWrapper = csvDealService.getInvalidDeals(fileName, pageRequest);

		logger.info(String.format("File search for InvalidDeals %s with param currentPage=%d, pageSize=%d", fileName,
				currentPage, pageSize));
		return dataWrapper;
	}

	@GetMapping(value = URLConstant.GET_ACCUMULATE_ORDERING_CURRENCY)
	public @ResponseBody DataWrapper getAccumulateOrderingCurrency(@RequestParam(required = false) int currentPage,
			@RequestParam(required = false) int pageSize) {
		int currentPageIndx = currentPage - 1;
		if (currentPageIndx < 0) {
			currentPageIndx = 0;
		}
		if (pageSize < 5)
			pageSize = 5;

		PageRequest pageRequest = new PageRequest(currentPageIndx, pageSize);
		DataWrapper dataWrapper = csvDealService.getAccumulateOrderingCurrency(pageRequest);

		logger.info("Search accumulate count for ordering currency");
		return dataWrapper;
	}

	@ResponseBody
	@DeleteMapping(value = URLConstant.DELETE_BY_FILE)
	public AppResponse delete(@RequestParam(required = true) String fileName) {
		csvDealService.deleteByFile(fileName);
		logger.info(String.format("File %s deleted successfully", fileName));
		return new AppResponse(AppConstant.SUCCESS_CODE, true, AppConstant.SUCCESS_MSG, null);
	}

	@ResponseBody
	@DeleteMapping(value = URLConstant.DELETE_ALL)
	public AppResponse delete() {
		csvDealService.deleteAll();
		logger.info(String.format("All files deleted successfully"));
		return new AppResponse(AppConstant.SUCCESS_CODE, true, AppConstant.SUCCESS_MSG, null);
	}

}
