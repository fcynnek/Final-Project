package com.fcynnek.finalproject.petmanagement.dto;

import java.time.LocalDate;

import com.fcynnek.finalproject.petmanagement.domain.Medication;

public class MedicationDTO {

	private Integer id;
	private String illness;
	private LocalDate medicationGiven;


	@Override
	public String toString() {
		return "MedicationDTO [id=" + id + ", illness=" + illness + ", medicationGiven=" + medicationGiven + "]";
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
