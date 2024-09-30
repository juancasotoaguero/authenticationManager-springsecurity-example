package com.nisum.api.restful.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.api.restful.domain.*;
import com.nisum.api.restful.entity.User;
import com.nisum.api.restful.exceptions.ResourceNotFoundException;
import com.nisum.api.restful.mapper.UserMapper;
import com.nisum.api.restful.repository.UserRepository;
import com.nisum.api.restful.service.AuthenticationService;
import com.nisum.api.restful.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserResponse signup(UserRequest request){
        LOGGER.info("Starting Signup request: {}", request);

        var dateCreated = new Date();
        User user = null;
        try {
            user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .createdDate(dateCreated)
                    .modifiedDate(dateCreated)
                    .lastLoginDate(dateCreated)
                    .phones(objectMapper.writeValueAsString(request.getPhones()))
                    .isActive(true)
                    .role(Role.USER).build();
        } catch (JsonProcessingException e) {
            return new UserResponse();
        }

        //verificar que no exista ya usuario con mismo email
        if(userRepository.findByEmail(request.getEmail()).isPresent())
            return new UserResponse();

        var jwt = jwtService.generateToken(user);
        user.setToken(jwt);
        User userCreated = userRepository.save(user);
        LOGGER.info("FINISHED Signup request");
        return UserMapper.mapToUserResponse(userCreated);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        LOGGER.info("Starting Signin request: {}", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        user.setToken(jwt);
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        LOGGER.info("JWT generated: {}", jwt);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public UserResponse modify(int id, UserRequest request) {
        //se verifica que exista
        LOGGER.info("Starting Modify user: {}", request);
        User userInstance = userRepository.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("ID no encontrado"));
        userInstance.setName(request.getName());
        userInstance.setEmail(request.getEmail());
        userInstance.setPassword(passwordEncoder.encode(request.getPassword()));
        userInstance.setModifiedDate(new Date());
        try {
            userInstance.setPhones(objectMapper.writeValueAsString(request.getPhones()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        User userUpdated = userRepository.save(userInstance);
        LOGGER.info("FINISHED Modify user");
        return UserMapper.mapToUserResponse(userUpdated);
    }

    @Override
    public UserResponse getById(int id) {
        LOGGER.info("Starting Get user: {}", id);
        User user = userRepository.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("ID no encontrado"));
        LOGGER.info("FINISHED Get user");
        return UserMapper.mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        LOGGER.info("Starting Get users");
        List<User> products = userRepository.findAll();
        LOGGER.info("FINISHED Get users");
        return products.stream().map(UserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }


    @Override
    public UserResponse delete(int id) {
        LOGGER.info("Starting Delete user: {}", id);
        User user = userRepository.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("ID no encontrado"));
        user.setIsActive(false);
        userRepository.save(user);
        LOGGER.info("FINISHED Delete user");
        return UserMapper.mapToUserResponse(user);
    }

}