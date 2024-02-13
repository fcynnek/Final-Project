package com.fcynnek.finalproject.petmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fcynnek.finalproject.petmanagement.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer>{

	@Query("SELECT a FROM Animal a WHERE a.id = :petId")
	Animal findPetById(@Param("petId") Integer petId);

	List<Animal> findByUsername(String username);

}
