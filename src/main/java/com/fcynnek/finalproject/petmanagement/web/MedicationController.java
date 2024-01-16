package com.fcynnek.finalproject.petmanagement.web;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;
import com.fcynnek.finalproject.petmanagement.repository.UserRepository;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import jakarta.annotation.PostConstruct;

import com.fcynnek.finalproject.petmanagement.service.AnimalService;
import com.fcynnek.finalproject.petmanagement.service.MedicationService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medication")
public class MedicationController {

	private AnimalService animalService;
	private AnimalRepository petRepo;
	private MedicationService medicationService;
	private MedsAndIllnessRepository medsAndIllnessRepo;
	private Logger logger = LoggerFactory.getLogger(MedicationController.class);
	
	public MedicationController(AnimalService animalService, AnimalRepository petRepo,
			MedicationService medicationService, MedsAndIllnessRepository medsAndIllnessRepo) {
		super();
		this.animalService = animalService;
		this.petRepo = petRepo;
		this.medicationService = medicationService;
		this.medsAndIllnessRepo = medsAndIllnessRepo;
	}


	@GetMapping("/")
	public String getMedication(Model model) {
		List<Medication> meds = medicationService.getAllMeds();
		model.addAttribute("meds", meds);
		model.addAttribute("med", new Medication());
		return "medication";
	}

	@PostMapping("/create")
	public String processAnimalForm(@ModelAttribute("animal") Animal animal, Model model) {
		animalService.save(animal);
		return "redirect:/pet/profile";
	}
	
	@GetMapping("/update/{id}")
	public String updateAnimal(Model model, @PathVariable Integer id) {
		Optional<Animal> pet = animalService.getById(id);
		model.addAttribute("animal", pet);
		return "pet_update";
	}
	
	@PostMapping("/update")
	public String updateAnimal(Animal animal) {
		animalService.save(animal);
		return "redirect:/pet/profile";
	}
	
	@PostMapping("/delete")
	public String deleteAnimal(Animal animal) {
		animalService.delete(animal);
		return "redirect:/pet/profile";
	}
}
