package com.fcynnek.finalproject.petmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {

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
		return "contact_form";
	}
	
	@PostMapping("/contact")
	public String submitContactUs () {
		
	}
	
	@GetMapping("/error")
	public String getError () {
		return "error";
	}

	@GetMapping("/error2")
	public String getError2 () {
		return "error2";
	}

}
