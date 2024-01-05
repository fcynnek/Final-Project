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
	
	@GetMapping("/mission")
	public String getMission () {
		return "mission";
	}
	
	@GetMapping("/benefits")
	public String getBenefits () {
		return "benefits";
	}
	
	@GetMapping("/contact")
	public String getContact () {
		return "contact";
	}
	
	
	@GetMapping("/error")
	public String getError () {
		return "error2";
	}

	@GetMapping("/error2")
	public String getError2 () {
		return "error2";
	}

}
