package com.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class is designed for REST service end-points for User which take
 * incoming request and perform corresponding business logic.
 * 
 * @author shyam.pareek
 * 
 */
@Controller
public class WebController {

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {

		return "welcome";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(Model model) {

		return "errorPage";
	}
}