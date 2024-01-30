package com.fcynnek.finalproject.petmanagement.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String illness;
	private String description;
	private String sideEffects;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate medicationGiven;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate medicationDue;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "animal_id")
	private Animal animal;
	
	
	@Override
	public String toString() {
		return "Medication [id=" + id + ", illness=" + illness + ", description=" + description + ", sideEffects="
				+ sideEffects + ", medicationGiven=" + medicationGiven + ", medicationDue=" + medicationDue
				+ ", animal=" + (animal != null ? animal.getId() : "null") + "]";
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(animal, description, id, illness, medicationDue, medicationGiven, sideEffects);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medication other = (Medication) obj;
		return Objects.equals(animal, other.animal) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(illness, other.illness)
				&& Objects.equals(medicationDue, other.medicationDue)
				&& Objects.equals(medicationGiven, other.medicationGiven)
				&& Objects.equals(sideEffects, other.sideEffects);
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
