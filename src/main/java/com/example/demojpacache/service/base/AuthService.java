package com.example.demojpacache.service.base;

import com.example.demojpacache.dto.request.LoginRequest;
import com.example.demojpacache.dto.response.GetRefreshTokenResponse;
import com.example.demojpacache.dto.response.JwtResponse;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest);

    GetRefreshTokenResponse refreshToken(String refreshToken);
}
