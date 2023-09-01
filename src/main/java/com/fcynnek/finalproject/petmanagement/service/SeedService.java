package com.fcynnek.finalproject.petmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;

@Service
public class SeedService {

	@Autowired
	private MedsAndIllnessRepository medsRepo;
}
