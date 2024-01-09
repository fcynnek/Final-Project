package com.fcynnek.finalproject.petmanagement.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.fcynnek.finalproject.petmanagement.repository.UserRepository;
import com.fcynnek.finalproject.petmanagement.security.AuthenticationServiceImpl;
import com.fcynnek.finalproject.petmanagement.security.JwtService;
import com.fcynnek.finalproject.petmanagement.security.SecurityConfig;
import com.fcynnek.finalproject.petmanagement.service.RefreshTokenService;
import com.fcynnek.finalproject.petmanagement.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

//@RestController
//@RequestMapping("/api/v1/auth")
@Controller
public class AuthenticationController {
	private final AuthenticationServiceImpl authenticationService;
	private final RefreshTokenService refreshTokenService;
	private final JwtService jwtService;
	private final UserServiceImpl userService;
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController(AuthenticationServiceImpl authenticationService,
			RefreshTokenService refreshTokenService, JwtService jwtService, UserServiceImpl userService,
			UserRepository userRepo, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationService = authenticationService;
		this.refreshTokenService = refreshTokenService;
		this.jwtService = jwtService;
		this.userService = userService;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/signin")
	public String getLogin(@ModelAttribute("user") User user) {
		return "login";
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request,
			@RequestBody User user) {
		Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
		String accessToken = jwtService.generateToken(user);

		return ResponseEntity.ok(authenticationService.signin(request));
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping("/authenticated")
	public String getUserDashboard(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			String username = principal.getName();
			Optional<User> authenticatedUser = userService.findUserByEmail(username);

			if (authenticatedUser.isPresent()) {
				model.addAttribute("user", authenticatedUser);

				if (authenticatedUser.get().getAuthorities().stream()
						.anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
					return "admin_dashboard";
				} else {
					return "user_dashboard";
				}
			} else {
				return "error";
			}
		} else {
			return "error";
		}
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

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtService.generateToken(user);
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				}).orElseThrow(() -> new IllegalStateException(
						"Refresh token " + requestRefreshToken + " is not in database!"));
	}

	@GetMapping("/profile")
	public String getProfile(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Optional<User> userProfile = userService.findUserByEmail(username);

		model.addAttribute("user", userProfile);
		return "profile";
	}

	@Transactional
	@PostMapping("/profile/update")
	public String update(@ModelAttribute("user") User user, Model model) {

		Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			User userInDB = existingUser.get();
			logger.info("Is existing user present? {}", userInDB.getEmail());

			String emailFromForm = user.getEmail();
			logger.info("Received request to update email from: {} to: {}", user.getEmail(), emailFromForm);

			if (!emailFromForm.equals(userInDB.getEmail()) && userService.existsByEmail(emailFromForm)) {
				model.addAttribute("updateError", "Email already exists. Please choose another email address.");
				return "profile";
			}

			// Update user details
			userInDB.setFirstName(user.getFirstName());
			userInDB.setLastName(user.getLastName());

			try {
				userService.updateByEmail(userInDB, emailFromForm);
				logger.info("Received request to update email to: {}", emailFromForm);
			} catch (DataIntegrityViolationException e) {
				logger.error("Error updating email", e);
				model.addAttribute("updateError", "Email already exists. Please choose another email address.");
				return "profile";
			} catch (Exception e) {
				logger.error("Error updating email", e);
				model.addAttribute("updateError", "Error updating email. Please try again.");
				return "profile";
			}

			// Update password if provided
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
			}

			userService.updateUser(userInDB);

			return "redirect:/authenticated";
		}
		return "redirect:/authenticated";
	}

	/*
	 * This code is from Trevor's original implementation which might be helpful for
	 * those who are not using server rendering templates
	 * 
	 * @PostMapping("/signin") public String authenticateLogin
	 * (@ModelAttribute("user") User user, SignInRequest request) { Optional<User>
	 * existingUser = userService.findUserByEmail(user.getEmail()); User loggedUser
	 * = ((User) userService).loadUserByUsername(user.getUsername()); String
	 * accessToken = jwtService.generateToken(user);
	 * 
	 * return ResponseEntity.ok(authenticationService.signin(request)); }
	 */

}