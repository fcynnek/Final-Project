package com.fcynnek.finalproject.petmanagement.web;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fcynnek.finalproject.petmanagement.dao.request.SignInRequest;
import com.fcynnek.finalproject.petmanagement.dao.request.RefreshTokenRequest;
import com.fcynnek.finalproject.petmanagement.dao.response.JwtAuthenticationResponse;
import com.fcynnek.finalproject.petmanagement.dao.response.TokenRefreshResponse;
import com.fcynnek.finalproject.petmanagement.domain.RefreshToken;
import com.fcynnek.finalproject.petmanagement.domain.User;
import com.fcynnek.finalproject.petmanagement.security.AuthenticationServiceImpl;
import com.fcynnek.finalproject.petmanagement.security.JwtService;
import com.fcynnek.finalproject.petmanagement.service.RefreshTokenService;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;

//@RestController
//@RequestMapping("/api/v1/auth")
@Controller
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserServiceImpl userService;
    
    public AuthenticationController(AuthenticationServiceImpl authenticationService,
			RefreshTokenService refreshTokenService, JwtService jwtService, UserServiceImpl userService) {
		super();
		this.authenticationService = authenticationService;
		this.refreshTokenService = refreshTokenService;
		this.jwtService = jwtService;
		this.userService = userService;
	}
    

    @GetMapping("/signin")
	public String getLogin (@ModelAttribute("user") User user) {
		return "login";
	}
    
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request, @RequestBody User user) {
    	Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
    	String accessToken = jwtService.generateToken(user);
    	
    	return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("/login-error")
    public String loginError (Model model) {
    	model.addAttribute("loginError", true);
    	return "login";
    }
    
    @GetMapping("/authenticated")
	public String getUserDashboard (Model model, User user) {
    	Optional<User> authenticatedUser = userService.findUserByEmail(user.getEmail());
    	model.addAttribute("user", authenticatedUser);
    	
		return "user_dashboard";
	}
    
    @GetMapping("/status")
    public ResponseEntity<String> getAuthenticatedStatus() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (auth != null && auth.isAuthenticated()) {
    		return ResponseEntity.ok("authenticated");
    	} else {
    		return ResponseEntity.ok("not_authenticated");
    	}
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest request) {
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
    
    @GetMapping("/medication") 
    public String getMedication() {
    	return "medication";
    }
    
    
    /*
     * This code is from Trevor's original implementation which might be helpful for those who are not using server rendering templates
     * 
     * @PostMapping("/signin") public String authenticateLogin
     * (@ModelAttribute("user") User user, SignInRequest request) { 
     * Optional<User> existingUser = userService.findUserByEmail(user.getEmail()); 
     * User loggedUser = ((User) userService).loadUserByUsername(user.getUsername()); 
     * String accessToken = jwtService.generateToken(user);
     * 
     * return ResponseEntity.ok(authenticationService.signin(request)); }
     */
    
}