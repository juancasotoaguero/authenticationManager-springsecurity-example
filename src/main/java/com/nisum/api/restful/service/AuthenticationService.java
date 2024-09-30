package com.nisum.api.restful.service;


import com.nisum.api.restful.domain.JwtAuthenticationResponse;
import com.nisum.api.restful.domain.UserRequest;
import com.nisum.api.restful.domain.SignInRequest;
import com.nisum.api.restful.domain.UserResponse;

import java.util.List;

public interface AuthenticationService {
    UserResponse signup(UserRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
    UserResponse modify(int id, UserRequest request);
    UserResponse getById(int id);
    List<UserResponse> getAll();
    UserResponse delete(int id);
}