package com.fcynnek.finalproject.petmanagement.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<com.fcynnek.finalproject.petmanagement.domain.User, Integer> {
    // Since email is unique, we'll find users by email
    Optional<com.fcynnek.finalproject.petmanagement.domain.User> findByEmail(String email);
}