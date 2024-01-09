package com.fcynnek.finalproject.petmanagement.service;

import java.util.List;

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

}
