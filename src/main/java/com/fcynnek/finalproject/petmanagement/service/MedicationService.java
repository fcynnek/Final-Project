package com.fcynnek.finalproject.petmanagement.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.dto.MedicationDTO;
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

	public List<Medication> getAllIllnesses(Medication medication) {
		return medsRepo.getAllIllnesses(medication);
	}
	
	public List<Medication> getAllIllnesses(String illness) {
		return medsRepo.getAllIllnesses(illness);
	}
	
	public Medication getByIllness(Medication medication) {
		return medsRepo.findByIllness(medication);
	}
	
	public void delete(Medication meds) {
		medsRepo.delete(meds);
	}
	
	public List<String> getIllnessList() {
		seedDataMeds();
		
		List<Medication> medications = medsRepo.findAll();
		List<String> illnessList = medications.stream()
				.map(Medication::getIllness)
				.distinct()
				.collect(Collectors.toList());
		
		return illnessList;
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
			
			
			for (String line : meds) {
				String[] values = line.split("\\s*\\|\\s*");
				
				Medication medication = new Medication();
				medication.setIllness(values[0].trim());
				medication.setDescription(values[1].trim());
				medication.setSideEffects(values[2].trim());
				
				medsRepo.save(medication);
			}
		}
	}

	
	public Medication convertDTOTOEntity(MedicationDTO medicationDTO) {
		Medication medication = new Medication();
		medication.setIllness(medicationDTO.getIllness());
		medication.setMedicationGiven(medicationDTO.getMedicationGiven());
		return medication;
	}
}
