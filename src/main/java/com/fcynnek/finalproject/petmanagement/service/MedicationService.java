package com.fcynnek.finalproject.petmanagement.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;

@Service
public class MedicationService {

	private MedsAndIllnessRepository medsRepo;


	public MedicationService(MedsAndIllnessRepository medsRepo) {
		super();
		this.medsRepo = medsRepo;
	}

	public List<Medication> getAllMeds() {
		return medsRepo.findAll();
	}

	public Medication save(Medication meds) {
		return medsRepo.save(meds);
	}

	public Medication getByName(String name) {
		return medsRepo.findByName(name);
	}
	
	public void delete(Medication meds) {
		medsRepo.delete(meds);
	}
	
	
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
				String[] values = line.split("|");
				medication.setIllness(values[0]);
				medication.setDescription(values[1]);
				medication.setSideEffects(values[2]);
				
				medsRepo.save(medication);
			}
		}
	}
}
