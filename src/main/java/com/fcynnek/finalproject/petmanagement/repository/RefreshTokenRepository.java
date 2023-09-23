package com.fcynnek.finalproject.petmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends JpaRepository<com.fcynnek.finalproject.petmanagement.domain.RefreshToken, Long> {
    Optional<com.fcynnek.finalproject.petmanagement.domain.RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(com.fcynnek.finalproject.petmanagement.domain.User user);
}