package com.fcynnek.finalproject.petmanagement.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String illness;
	private String description;
	private String sideEffects;
	private LocalDate medicationGiven;
	private LocalDate medicationDue;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Animal animal;
	
	
	
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSideEffects() {
		return sideEffects;
	}
	
	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}

	public LocalDate getMedicationGiven() {
		return medicationGiven;
	}

	public void setMedicationGiven(LocalDate medicationGiven) {
		this.medicationGiven = medicationGiven;
	}

	public LocalDate getMedicationDue() {
		return medicationDue;
	}

	public void setMedicationDue(LocalDate medicationDue) {
		this.medicationDue = medicationDue;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
}
