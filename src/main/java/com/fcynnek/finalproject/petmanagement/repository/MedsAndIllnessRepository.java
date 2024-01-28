package com.fcynnek.finalproject.petmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcynnek.finalproject.petmanagement.domain.Medication;

@Repository
public interface MedsAndIllnessRepository extends JpaRepository<Medication, Integer> {

	Medication findByIllness(String medication);
	
	

}
