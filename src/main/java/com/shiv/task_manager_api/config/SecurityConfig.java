package com.shiv.task_manager_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shiv.task_manager_api.security.JwtAuthenticationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
    
}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
        .csrf(csrf->csrf.disable())
               .authorizeHttpRequests(auth-> auth
                .requestMatchers("/tasks/auth/**").permitAll()

                .requestMatchers(HttpMethod.DELETE,"/tasks/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/tasks/**")
                .hasAnyRole("USER","ADMIN")

                .requestMatchers(HttpMethod.PUT,"/tasks/**")
                .hasAnyRole("USER","ADMIN")

                .requestMatchers(HttpMethod.GET,"/tasks/**")
                .hasAnyRole("USER","ADMIN")
                

                .anyRequest().authenticated()
               )
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
}

@Bean
public AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration)
    throws Exception{
        return configuration.getAuthenticationManager();
    }
}