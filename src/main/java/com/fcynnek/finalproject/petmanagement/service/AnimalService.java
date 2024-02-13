package com.fcynnek.finalproject.petmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.User;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;

@Service
public class AnimalService {

	private AnimalRepository petRepo;
	private UserServiceImpl userService;

	public AnimalService(AnimalRepository petRepo, UserServiceImpl userService) {
		super();
		this.petRepo = petRepo;
		this.userService = userService;
	}

	public List<Animal> getAllPets() {
		return petRepo.findAll();
	}

	public Animal save(Animal pet, String email) {
		Optional<User> user = userService.findUserByEmail(email);
		user.ifPresent(pet::setUser);
		return petRepo.save(pet);
	}

	public Optional<Animal> getById(Integer id) {
		return petRepo.findById(id);
	}

	public Animal getByPetId(Integer petId) {
		Optional<Animal> pet = petRepo.findById(petId);
		return pet.orElse(null);
	}

	@Secured("ROLE_ADMIN")
	public void delete(Animal animal) {
		petRepo.delete(animal);
	}

	public List<Animal> getAnimalByUsername(String email) {
		return petRepo.findByUserEmail(email);
	}
}
