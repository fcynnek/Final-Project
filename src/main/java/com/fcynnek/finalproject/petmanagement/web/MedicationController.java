package com.fcynnek.finalproject.petmanagement.web;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.dto.MedicationDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/pet/medication")
@SessionAttributes("id")
public class MedicationController {

	private AnimalService animalService;
	private AnimalRepository animalRepo;
	private MedicationService medicationService;
	private MedsAndIllnessRepository medsAndIllnessRepo;
	private Logger logger = LoggerFactory.getLogger(MedicationController.class);

	public MedicationController(AnimalService animalService, AnimalRepository animalRepo,
			MedicationService medicationService, MedsAndIllnessRepository medsAndIllnessRepo) {
		super();
		this.animalService = animalService;
		this.animalRepo = animalRepo;
		this.medicationService = medicationService;
		this.medsAndIllnessRepo = medsAndIllnessRepo;
	}

	@GetMapping("")
	public String getPets(Model model) {
		List<Animal> pets = animalService.getAllPets();
		logger.info("Inside getPets method");

		List<Medication> meds = medicationService.getAllMeds();

		model.addAttribute("animals", pets);
		model.addAttribute("medications", meds);
		return "medication";
	}

	@GetMapping("/create/{id}")
	public String showMedications(ModelMap model, @PathVariable Integer id) {
		System.out.println("About to fetch Animal ID#: " + id);
		Animal animal = animalService.getByPetId(id);
		List<Medication> medications = medicationService.getAllMedsForPet(animal);
		List<String> illnessList = medicationService.getIllnessList();
		
		model.addAttribute("id", id);
		model.addAttribute("animal", animal);
		model.addAttribute("medications", medications);
		model.addAttribute("newMedication", new Medication());
		model.addAttribute("illnessList", illnessList);
		return "medication_create";
	}
	
	@PostMapping("/create/{id}")
	public String processMedsForm(@PathVariable("id") Integer id, @ModelAttribute("newMedication") Medication newMedication) {
		logger.info("Animal ID: ", id);
		
		medicationService.saveMedication(newMedication, id);
		return "redirect:/pet/medication/create/{id}";
	}


	@PostMapping("/delete")
	public String deleteMedication(Medication medication) {
		medicationService.delete(medication);
		return "redirect:/pet/medication";
	}
}
