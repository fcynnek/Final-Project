package com.fcynnek.finalproject.petmanagement.web;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fcynnek.finalproject.petmanagement.domain.ContactForm;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;

@Controller
public class DefaultController {

	private UserServiceImpl userService;
	private ContactForm contactForm;

	
	public DefaultController(UserServiceImpl userService, ContactForm contactForm) {
		super();
		this.userService = userService;
		this.contactForm = contactForm;
	}
	

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
	public String getContact (Model model) {
		model.addAttribute("contactForm", new ContactForm());
		return "contact_form";
	}
	
	@PostMapping("/contact")
	public String submitContactUs (@ModelAttribute("contactForm") ContactForm contact,
//			@RequestParam("contactForm") ContactForm contact, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			RedirectAttributes redirectAttributes) {
		userService.saveContactFormData(contact);
		redirectAttributes.addFlashAttribute("formSubmitted", "Your message has been received");
		return "redirect:/contact";
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
