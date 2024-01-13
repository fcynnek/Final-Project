package com.fcynnek.finalproject.petmanagement.web;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;
import com.fcynnek.finalproject.petmanagement.repository.UserRepository;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.fcynnek.finalproject.petmanagement.service.AnimalService;

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

	public void test() {
		// TODO(developer): Uncomment these lines.
		// import com.google.cloud.translate.*;
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Translation translation = translate.translate("¡Hola Mundo!");

		System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
	}

	@GetMapping("/medication")
	public String getMedication() {
		return "medication";
	}

	@GetMapping("/features")
	public String getFeatures() {
		return "pet_features";
	}

	@ModelAttribute("speciesList")
	public Animal.Species[] speciesList() {
		return Animal.Species.values();
	}

	@GetMapping("/profile")
	public String getProfile(Model model) {
		List<Animal> pets = animalService.getAllPets();
		model.addAttribute("animals", pets);
		model.addAttribute("animal", new Animal());
		return "pet_profile";
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
