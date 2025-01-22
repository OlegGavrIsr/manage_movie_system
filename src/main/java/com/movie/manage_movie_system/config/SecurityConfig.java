package com.movie.manage_movie_system.config;

import com.movie.manage_movie_system.enums.UserRole;
import com.movie.manage_movie_system.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static java.nio.file.attribute.AclEntryType.DENY;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/auth/**").permitAll() // Example: Permit requests to authentication endpoints
                        .requestMatchers("/h2-console/**").permitAll() // Example: Permit requests to H2 console
                        .requestMatchers("/movies/**").hasAuthority(UserRole.ADMIN.name()) // Example: Permit requests to movie endpoints only for ADMIN
                        .requestMatchers("/showtime/**").hasAuthority(UserRole.ADMIN.name()) // Example: Permit requests to showtime endpoints only for ADMIN
                        .requestMatchers("/booking/**").hasAuthority(UserRole.CUSTOMER.name()) // Example: Permit requests to booking endpoints only for CUSTOMER
                        .anyRequest().authenticated()

                        // Require authentication for all other requests

                )
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .formLogin(form -> form
//                        .loginPage("/auth/signin")
//                        .permitAll() // Customize login page URL
//                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }
}
