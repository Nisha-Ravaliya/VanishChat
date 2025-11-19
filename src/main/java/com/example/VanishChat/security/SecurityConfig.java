package com.example.VanishChat.security;

import com.example.VanishChat.JWToolkit.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(BCryptPasswordEncoder passwordEncoder) {
        UserDetails adminUser = User.withUsername("nisha")
                .password(passwordEncoder.encode("nisha@2005"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.withUsername("user1")
                .password(passwordEncoder.encode("user1pass"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/register",
                                "/api/login", "/api/register",
                                "/Privacy", "/verify",
                                "/css/**", "/js/**", "/images/**",
                                "/dashboard"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // ✅ Add your JWT filter BEFORE Spring's UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
