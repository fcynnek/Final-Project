package com.fcynnek.finalproject.petmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;

@Service
public class AnimalService {

	private AnimalRepository petRepo;
	
	
	public AnimalService(AnimalRepository petRepo) {
		super();
		this.petRepo = petRepo;
	}



	public List<Animal> getAllPets() {
		return petRepo.findAll();
	}

	public Animal save(Animal pet) {
		return petRepo.save(pet);
	}

//	public Animal getByName(String name) {
//		return petRepo.findByName(name);
//	}
	
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
		// TODO Auto-generated method stub
		return null;
	}
}
