package com.sample.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is designed error mapping path
 * 
 * @author shyam.pareek
 *
 */
@RestController
public class SimpleErrorController implements ErrorController {

	private static final String PATH = "/errorPage";

	@Override
	public String getErrorPath() {
		return PATH;
	}
}