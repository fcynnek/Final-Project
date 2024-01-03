package com.fcynnek.finalproject.petmanagement.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String illness;
	private String description;
	private String sideEffects;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Animal relatedAnimal;
	
}
