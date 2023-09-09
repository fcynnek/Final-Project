package com.fcynnek.finalproject.petmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String getWelcome () {
		return "index";
	}
	
	@GetMapping("/login")
	public String getLogin () {
		return "login";
	}
	
	@GetMapping("/about")
	public String getAbout () {
		return "about";
	}
	
	@GetMapping("/vision")
	public String getVision () {
		return "vision";
	}
}
