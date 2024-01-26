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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pet/medication")
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


	@GetMapping("")
	public String getPets(Model model) {
		List<Animal> pets = animalService.getAllPets();
		logger.info("Inside getPets method");
		
		List<Medication> meds = medicationService.getAllMeds();
		
		model.addAttribute("animals", pets);
		model.addAttribute("medications", meds);
		return "medication";
	}

	@PostMapping("/create")
	public String processMedsForm(@ModelAttribute("medication") Medication medication, Model model) {
		Medication fetchMedication =  medicationService.getByIllness(medication);
		logger.info("Received medication: {}", medication);
		
		LocalDate medicationGiven = medication.getMedicationGiven();
		LocalDate medicationDue = medicationGiven.plusYears(1);
		
		if (!fetchMedication.equals(null)) {
		    Medication firstMedication = fetchMedication.getIllness();
		    medication.setDescription(firstMedication.getDescription());
		    medication.setSideEffects(firstMedication.getSideEffects());
		    medication.setMedicationDue(medicationDue);
		}
		medicationService.save(medication);
		return "medication_create";
	}
	
	@GetMapping("/{id}")
	public String showMedications(Model model, @PathVariable Integer id) {
		List<Medication> meds = medicationService.getAllMeds();
		List<String> illnessList = medicationService.getIllnessList();
		model.addAttribute("medications", meds);
		model.addAttribute("medication", new Medication());
		model.addAttribute("illnessList", illnessList);
		return "medication_create";
	}
	
//	@GetMapping("/details")
//	@ResponseBody
//	public ResponseEntity<Medication> getMedicationDetails(@RequestParam String illness) {
//		Medication medication = medicationService.getByIllness(illness);
//		
//		if (medication != null) {
//			return ResponseEntity.ok(medication);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
	
	@PostMapping("/delete")
	public String deleteMedication(Medication medication) {
		medicationService.delete(medication);
		return "redirect:/pet/medication";
	}
}
