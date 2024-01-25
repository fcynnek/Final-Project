package com.fcynnek.finalproject.petmanagement.dto;

import java.time.LocalDate;

import com.fcynnek.finalproject.petmanagement.domain.Medication;

public class MedicationDTO {

	private String illness;
	private LocalDate medicationGiven;
	
	
	public MedicationDTO(String illness, LocalDate medicationGiven) {
		super();
		this.illness = illness;
		this.medicationGiven = medicationGiven;
	}


	public String getIllness() {
		return illness;
	}


	public void setIllness(String illness) {
		this.illness = illness;
	}


	public LocalDate getMedicationGiven() {
		return medicationGiven;
	}


	public void setMedicationGiven(LocalDate medicationGiven) {
		this.medicationGiven = medicationGiven;
	}
	
}
