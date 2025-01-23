package com.movie.manage_movie_system.config;

import com.movie.manage_movie_system.enums.UserRole;
import com.movie.manage_movie_system.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                        .requestMatchers("\"/swagger-ui/**\", \"/v3/api-docs/**\"").permitAll() // Example: Permit requests to swagger-ui console
                        .requestMatchers("/h2-console/**").permitAll() // Example: Permit requests to H2 console
                        .requestMatchers("/movies/**").hasAuthority(UserRole.ADMIN.name()) // Example: Permit requests to movie endpoints only for ADMIN
                        .requestMatchers("/showtime/**").hasAuthority(UserRole.ADMIN.name()) // Example: Permit requests to showtime endpoints only for ADMIN
                        .requestMatchers("/booking/**").hasAuthority(UserRole.CUSTOMER.name()) // Example: Permit requests to booking endpoints only for CUSTOMER
                        .requestMatchers("/auth/**").permitAll() // Example: Permit requests to authentication endpoints
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(form -> form
//                        .loginPage("/auth/signin")
//                        .permitAll() // Customize login page URL
//                        .defaultSuccessUrl("/home")
//                        .failureUrl("/login?error")
//                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("adminUser")
                .password(passwordEncoder().encode("passwordAdmin"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("customerUser")
                .password(passwordEncoder().encode("passwordCustomer"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
