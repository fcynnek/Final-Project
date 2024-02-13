package com.fcynnek.finalproject.petmanagement.service;

import java.util.List;
import java.util.Optional;

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

	public Animal save(Animal pet, String username) {
		Optional<User> user = userService.findUserByEmail(username);
		user.ifPresent(pet::setUser);
//		pet.setUser(user);
		return petRepo.save(pet);
	}
	
	public Optional<Animal> getById(Integer id) {
		return petRepo.findById(id);
	}
	
	public Animal getByPetId(Integer petId) {
		Optional<Animal> pet = petRepo.findById(petId);
		return pet.orElse(null);
	}
	
	public void delete(Animal animal) {
		petRepo.delete(animal);
	}

	public List<Animal> getAnimalByUsername(String username) {

		return petRepo.findByUsername(username);
	}
}
