package com.yassine.users.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             BCryptPasswordEncoder bCryptPasswordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(bCryptPasswordEncoder)
                   .and()
                   .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Collections.singletonList("https://touche-tout.vercel.app")); // Remplacez par votre frontend
                    cors.setAllowedMethods(Collections.singletonList("*"));
                    cors.setAllowCredentials(true);
                    cors.setAllowedHeaders(Collections.singletonList("*"));
                    cors.setExposedHeaders(Collections.singletonList("Authorization"));
                    cors.setMaxAge(3600L);
                    return cors;
                }
            }))
            .authorizeHttpRequests()
            .requestMatchers("/api/services/login", "/api/services/allService", "/api/services/upload-image",
                    "/api/services/add/{userId}", "/api/services/user/{userId}",
                    "/api/services/update/{serviceId}", "/api/services/delete/{serviceId}")
            .permitAll()
        	// Autorise uniquement le login sans authentification
            .requestMatchers("/register/**", "/verifyEmail/**") // Exige authentification pour ces endpoints
            .permitAll().anyRequest().authenticated(); // Autorise tous les autres endpoints
        
        return http.build();
    }
}
