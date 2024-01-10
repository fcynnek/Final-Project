package com.fcynnek.finalproject.petmanagement.web;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;
import com.fcynnek.finalproject.petmanagement.repository.UserRepository;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;
import com.fcynnek.finalproject.petmanagement.service.AnimalService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pet")
public class AnimalController {
	
    private UserServiceImpl userService;
    private UserRepository userRepo;
    private AnimalService animalService;
    private AnimalRepository petRepo;
    private MedsAndIllnessRepository medsAndIllnessRepo;
    private Logger logger = LoggerFactory.getLogger(AnimalController.class);
    
    
    public AnimalController(UserServiceImpl userService, UserRepository userRepo, AnimalRepository petRepo,
			AnimalService animalService, MedsAndIllnessRepository medsAndIllnessRepo) {
		super();
		this.userService = userService;
		this.userRepo = userRepo;
		this.petRepo = petRepo;
		this.animalService = animalService;
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
    
	@ModelAttribute("speciesList")
	public Animal.Species[] speciesList() {
		return Animal.Species.values();
	}

	@GetMapping("/profile") 
	public String getProfile(Model model) {
//		List<Animal> pets = AnimalService.getAllPets();
//		model.addAttribute("pets", pets);
		return "pet_profile";
	}
	
	@GetMapping("/create")
	public String showAnimalForm(Model model) {
		model.addAttribute("pets", new Animal());
		return "pet_profile";
	}
	
	@PostMapping("/create")
	public String processAnimalForm(@ModelAttribute("animal") Animal pet, Model model) {
		
		return "redirect:/pet_profile";
	}
}
