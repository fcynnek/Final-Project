package com.fcynnek.finalproject.petmanagement.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fcynnek.finalproject.petmanagement.dao.request.SignInRequest;
import com.fcynnek.finalproject.petmanagement.dao.request.SignUpRequest;
import com.fcynnek.finalproject.petmanagement.dao.response.JwtAuthenticationResponse;
import com.fcynnek.finalproject.petmanagement.domain.Authority;
import com.fcynnek.finalproject.petmanagement.domain.Role;
import com.fcynnek.finalproject.petmanagement.domain.User;
import com.fcynnek.finalproject.petmanagement.service.RefreshTokenService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final com.fcynnek.finalproject.petmanagement.repository.UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final com.fcynnek.finalproject.petmanagement.service.RefreshTokenService refreshTokenService;
    
    public AuthenticationServiceImpl(com.fcynnek.finalproject.petmanagement.repository.UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = new User()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authority(Role.USER.name()).build();
        request.authorityOpt().ifPresent(auth -> user.getAuthorities().add(new Authority(auth, user)));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new JwtAuthenticationResponse(jwt, refreshToken.getToken());
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshTokenOpt = refreshTokenService.findByToken(jwt);
        if (refreshTokenOpt.isPresent()) {
            return new JwtAuthenticationResponse(jwt, refreshTokenOpt.get().getToken()); 
        } else {
            return new JwtAuthenticationResponse(jwt, refreshTokenService.createRefreshToken(user.getId()).getToken());
        }
    }
}