package com.nisum.api.controller;


import com.nisum.api.restful.controller.AuthenticationController;
import com.nisum.api.restful.domain.JwtAuthenticationResponse;
import com.nisum.api.restful.domain.SignInRequest;
import com.nisum.api.restful.domain.UserRequest;
import com.nisum.api.restful.domain.UserResponse;
import com.nisum.api.restful.service.AuthenticationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private UserRequest userRequest;
    private SignInRequest signinRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        signinRequest = new SignInRequest();
        userResponse = new UserResponse();
        ReflectionTestUtils.setField(authenticationController,
                "regularExpression",
                "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
    }

    @SneakyThrows
    @Test
    void testSignup() {
        userResponse.setToken("adlksjdlaj");
        userRequest.setEmail("prueba@gmail.com");
        when(authenticationService.signup(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<Object> responseEntity = authenticationController.signup(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userResponse, responseEntity.getBody());
    }

    @SneakyThrows
    @Test
    void testSignupNotAcceptable() {
        userResponse.setToken(null);
        userRequest.setEmail("prueba@gmail.com");
        when(authenticationService.signup(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<Object> responseEntity = authenticationController.signup(userRequest);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    void testSignin() {
        when(authenticationService.signin(any(SignInRequest.class))).thenReturn(new JwtAuthenticationResponse());

        ResponseEntity<JwtAuthenticationResponse> responseEntity = authenticationController.signin(signinRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new JwtAuthenticationResponse(), responseEntity.getBody());
    }
}