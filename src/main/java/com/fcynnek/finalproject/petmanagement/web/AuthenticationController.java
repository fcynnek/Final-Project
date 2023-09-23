package com.fcynnek.finalproject.petmanagement.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcynnek.finalproject.petmanagement.dao.request.SignInRequest;
import com.fcynnek.finalproject.petmanagement.dao.request.SignUpRequest;
import com.fcynnek.finalproject.petmanagement.dao.request.TokenRefreshRequest;
import com.fcynnek.finalproject.petmanagement.dao.response.JwtAuthenticationResponse;
import com.fcynnek.finalproject.petmanagement.dao.response.TokenRefreshResponse;
import com.fcynnek.finalproject.petmanagement.domain.RefreshToken;
import com.fcynnek.finalproject.petmanagement.security.AuthenticationService;
import com.fcynnek.finalproject.petmanagement.security.JwtService;
import com.fcynnek.finalproject.petmanagement.service.RefreshTokenService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    
    public AuthenticationController(AuthenticationService authenticationService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        super();
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
      String requestRefreshToken = request.refreshToken();

      return refreshTokenService.findByToken(requestRefreshToken)
          .map(refreshTokenService::verifyExpiration)
          .map(RefreshToken::getUser)
          .map(user -> {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
          })
          .orElseThrow(() -> new IllegalStateException(
              "Refresh token " + requestRefreshToken + " is not in database!"));
    }
}