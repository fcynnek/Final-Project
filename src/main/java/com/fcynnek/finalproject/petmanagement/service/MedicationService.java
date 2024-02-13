package com.fcynnek.finalproject.petmanagement.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.domain.Animal;
import com.fcynnek.finalproject.petmanagement.domain.Medication;
import com.fcynnek.finalproject.petmanagement.repository.AnimalRepository;
import com.fcynnek.finalproject.petmanagement.repository.MedsAndIllnessRepository;

@Service
public class MedicationService {

	private MedsAndIllnessRepository medsRepo;
	private AnimalService animalService;

	public MedicationService(MedsAndIllnessRepository medsRepo, AnimalService animalService) {
		super();
		this.medsRepo = medsRepo;
		this.animalService = animalService;
	}

	public List<Medication> getAllMeds() {
		return medsRepo.findAll();
	}

	public Medication save(Medication meds) {
		return medsRepo.save(meds);
	}

	public Medication findByIllness(String medication) {
		return medsRepo.findByIllness(medication);
	}

	@Secured("ROLE_ADMIN")
	public void delete(Medication meds) {
		medsRepo.delete(meds);
	}

	public List<Medication> getAllMedsForPet(Animal pet) {
		List<Medication> petIllness = pet.getMeds();
		return petIllness;
	}

	public List<String> getIllnessList() {
		seedDataMeds();

		List<Medication> medications = medsRepo.findAll();
		List<String> illnessList = medications.stream().map(Medication::getIllness).distinct()
				.collect(Collectors.toList());

		return illnessList;
	}

	private String[] getData(String filepath) {
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

	private void seedDataMeds() {
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

	public String getDescriptionByIllness(String illness) {
		Medication getIllnessData = findByIllness(illness);
		return getIllnessData.getDescription();
	}

	public String getSideEffectsByIllness(String illness) {
		Medication getIllnessData = findByIllness(illness);
		return getIllnessData.getSideEffects();
	}

	public void saveMedication(Medication newMedication, Integer id) {
		Medication medication = new Medication();

		medication.setIllness(newMedication.getIllness());
		medication.setDescription(getDescriptionByIllness(newMedication.getIllness()));
		medication.setSideEffects(getSideEffectsByIllness(newMedication.getIllness()));
		medication.setMedicationGiven(newMedication.getMedicationGiven());
		medication.setMedicationDue(newMedication.getMedicationGiven().plusYears(1));

		Animal animal = animalService.getByPetId(id);
		medication.setAnimal(animal);

		medsRepo.save(medication);

	}
}
