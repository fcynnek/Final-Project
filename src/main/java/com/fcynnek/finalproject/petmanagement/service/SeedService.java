package com.fcynnek.finalproject.petmanagement.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;

@Service
public class SeedService {

	@Autowired
	private MedsAndIllnessRepository medsRepo;
	
	
	private String[] getData (String filepath) {
		List<String> data;
		String[] dataArray = null;
		
		try {
			data = Files.readAllLines(Paths.get(filepath));
			dataArray = data.toArray(new String[0]);
			
			for (String line : dataArray) {
				System.out.println(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}
	
	
	private void seedDataMeds () {
		
		List<Medication> medications = medsRepo.findAll();
		if (medications.size() < 10) {
			String[] meds = getData("src/main/resources/data/SeedData.txt");
			
			for (int i = 0; i < 100; i++) {
				Medication medication = new Medication();
				
				String line = meds[(meds.length - 1)];
				medication.setIllness(line.substring(0, line.indexOf("|")));
				medication.setDescription(line.indexOf("|") + 1, )
			}
		}
	}
}
