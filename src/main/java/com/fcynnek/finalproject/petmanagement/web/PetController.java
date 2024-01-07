package com.fcynnek.finalproject.petmanagement.web;

import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;
import com.fcynnek.finalproject.petmanagement.repository.PetRepository;
import com.fcynnek.finalproject.petmanagement.repository.UserRepository;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//@RestController
@Controller
@RequestMapping("/pet")
public class PetController {
    private UserServiceImpl userService;
    private UserRepository userRepo;
    private PetRepository petRepo;
    private MedsAndIllnessRepository medsAndIllnessRepo;
    private Logger logger = LoggerFactory.getLogger(PetController.class);
    
    public PetController(UserServiceImpl userService, UserRepository userRepo, PetRepository petRepo,
			MedsAndIllnessRepository medsAndIllnessRepo) {
		super();
		this.userService = userService;
		this.userRepo = userRepo;
		this.petRepo = petRepo;
		this.medsAndIllnessRepo = medsAndIllnessRepo;
	}


	@GetMapping("/medication")
	public String getMedication() {
		return "medication";
	}
	
	@GetMapping("/features")
	public String getFeatures () {
		return "pet_features";
	}
    



}
