package com.nisum.api.restful.controller;

import com.nisum.api.restful.domain.*;
import com.nisum.api.restful.exceptions.ResourceNotFoundException;
import com.nisum.api.restful.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("email.validate")
    private String regularExpression;

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> signup(@RequestBody UserRequest request){
        //Se valida el email
        if(patternMatches(request.getEmail(), regularExpression))
            return new ResponseEntity<>(new ErrorResponse("Formato de email incorrecto"), HttpStatus.BAD_REQUEST);

        UserResponse response = authenticationService.signup(request);

        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id){
        try {
            UserResponse userResponse = authenticationService.getById(id);
            return  ResponseEntity.ok(userResponse);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> userList = authenticationService.getAll();
        return  ResponseEntity.ok(userList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer id, @RequestBody UserRequest signInRequest){
        try {
            UserResponse userUpdated = authenticationService.modify(id, signInRequest);
            return ResponseEntity.ok(userUpdated);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteProduct( @PathVariable Integer id){
        try {
            UserResponse userDeleted = authenticationService.delete(id);
            return ResponseEntity.ok(userDeleted);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}