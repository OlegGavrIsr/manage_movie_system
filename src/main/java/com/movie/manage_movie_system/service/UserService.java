package com.movie.manage_movie_system.service;

import com.movie.manage_movie_system.dto.request.UserEntryDto;
import com.movie.manage_movie_system.model.User;
import com.movie.manage_movie_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserEntryDto signUpRequest){
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User Already Exists with this Username");
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User Already Exists with this Email");
        }

        String encryptedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .password(encryptedPassword)
                .role(signUpRequest.getRole())
                .email(signUpRequest.getEmail())
                .build();

        userRepository.save(user);
        return "User Saved Successfully";

    }
}
