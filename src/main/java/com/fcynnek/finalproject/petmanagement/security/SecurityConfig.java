package com.fcynnek.finalproject.petmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fcynnek.finalproject.petmanagement.domain.Role;
import com.fcynnek.finalproject.petmanagement.service.UserService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    
    public SecurityConfig (JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(request -> request.requestMatchers("**").permitAll().anyRequest().authenticated())
                .authorizeHttpRequests(request ->
                                request.requestMatchers("/").permitAll()
                                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                        .requestMatchers("/dashboard").authenticated()
                                        .anyRequest().permitAll()
                                        	/*
                                        	 * remove the h2 db from line 42
                                        	 * then change application.properties to mysql
                                        	 * then change line 44 to .authenticated() or hasRole()
                                        	 */
//                .requestMatchers()
//                .antMatchers("/api/v1/auth/**")
//                .antMatchers("/admin/**")
//                .antMatchers("/h2-console/**")
//                .antMatchers("/dashboard")
//                .and()
//                .authorizeRequests()
//                    .antMatchers("/api/v1/auth/**").permitAll()
//                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
//                    .antMatchers("/h2-console/**").permitAll()
//                    .antMatchers("/dashboard").authenticated()
//                    .anyRequest().permitAll()

                    
                        )
                .headers(header -> header.frameOptions(frameOption -> frameOption.disable()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}