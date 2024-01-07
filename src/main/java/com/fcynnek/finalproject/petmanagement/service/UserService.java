package com.fcynnek.finalproject.petmanagement.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fcynnek.finalproject.petmanagement.domain.User;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<User> findAll();
    boolean existsByEmail(String email);
}