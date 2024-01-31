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

	@PostMapping("/create")
	public String processMedsForm(@ModelAttribute("medicationDTO") MedicationDTO medicationDTO,
			@RequestParam("medicationGiven") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate medicationGiven,
			@RequestParam("id") Integer animalId,
			Model model) {
		Medication medication = medicationService.convertDTOTOEntity(medicationDTO);
		logger.info("Received medication: {}", medicationDTO);
		logger.info("Received ID from DTO: {}", medicationDTO.getId());

//		Integer petId = (Integer) model.getAttribute("id");
//		logger.info("ID received from Model: {}", petId);
//		Animal animal = animalService.getByPetId(petId);
//		logger.info("Animal received from Model and AnimalService: {}", animal);
		Animal animal = animalService.getByPetId(animalId);
		logger.info("ID received from Param: {}", animalId);
		if (animal == null) {

			return "redirect:/error";
		} else {
			List<String> illnesses = animal.getIllnesses();
			if (illnesses == null) {
				illnesses = new ArrayList<>();
			}
			illnesses.add(medicationDTO.getIllness());
			animal.setIllnesses(illnesses);
		}

		LocalDate medicationDue = medicationGiven.plusYears(1);
		String illness = medication.getIllness();
		Medication fetchMedication = medicationService.getByIllness(illness);

		if (fetchMedication != null && animal != null) {
			medication.setIllness(illness);
			medication.setDescription(medication.getDescription());
			medication.setSideEffects(medication.getSideEffects());
			medication.setMedicationGiven(medicationGiven);
			medication.setMedicationDue(medicationDue);
			medication.setAnimal(animal);
		}
		animalRepo.save(animal);
		medicationService.save(medication);

		return "redirect:/pet/medication";
	}

	@GetMapping("/{id}")
	public String showMedications(Model model, @PathVariable Integer id) {
		Animal pet = animalService.getByPetId(id);
		List<Medication> meds = medicationService.getAllMedsForPet(pet);
		List<String> illnessList = medicationService.getIllnessList();
		
		model.addAttribute("id", id);
		model.addAttribute("medications", meds);
		model.addAttribute("medicationDTO", new Medication());
		model.addAttribute("illnessList", illnessList);
		return "medication_create";
	}

	@PostMapping("/delete")
	public String deleteMedication(Medication medication) {
		medicationService.delete(medication);
		return "redirect:/pet/medication";
	}
}
