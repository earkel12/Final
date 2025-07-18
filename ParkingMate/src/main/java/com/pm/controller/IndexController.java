package com.pm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/header")
	public String header() {
		return "header";
	}
	
	@GetMapping("/companyProfile")
	public String companyProfile() {
		return "companyProfile";
	}
}
