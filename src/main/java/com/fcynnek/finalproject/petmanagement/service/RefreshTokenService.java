package com.fcynnek.finalproject.petmanagement.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
  @Value("${token.refreshExpiration}")
  private Long refreshTokenDurationMs;

  @Autowired
  private com.fcynnek.finalproject.petmanagement.repository.RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private com.fcynnek.finalproject.petmanagement.repository.UserRepository userRepository;

  public Optional<com.fcynnek.finalproject.petmanagement.domain.RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public com.fcynnek.finalproject.petmanagement.domain.RefreshToken createRefreshToken(Integer userId) {
    com.fcynnek.finalproject.petmanagement.domain.RefreshToken refreshToken = new com.fcynnek.finalproject.petmanagement.domain.RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public com.fcynnek.finalproject.petmanagement.domain.RefreshToken verifyExpiration(com.fcynnek.finalproject.petmanagement.domain.RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new IllegalStateException("Refresh token " + token.getToken() + " was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Integer userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}