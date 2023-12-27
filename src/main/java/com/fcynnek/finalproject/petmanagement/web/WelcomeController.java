package com.fcynnek.finalproject.petmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/home")
	public String getWelcome () {
		return "index";
	}
	
	@GetMapping("/about")
	public String getAbout () {
		return "about";
	}
	
//	@GetMapping("/vision")
//	public String getVision () {
//		return "vision";
//	}
	
	@GetMapping("/dashboard")
	public String getDashboard () {
		return "dashboard";
	}
	
	@GetMapping("/test")
	public String getTest () {
		return "test";
	}
	
	@GetMapping("/error")
	public String getError () {
		return "error";
	}

	@GetMapping("/error2")
	public String getError2 () {
		return "error2";
	}

	// https://sweetalert2.github.io/#examples
}
