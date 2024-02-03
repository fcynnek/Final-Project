package com.fcynnek.finalproject.petmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcynnek.finalproject.petmanagement.domain.ContactForm;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm	, Integer>{

}
