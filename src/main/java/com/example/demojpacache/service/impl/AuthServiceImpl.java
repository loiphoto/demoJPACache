package com.example.demojpacache.service.impl;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.dto.request.LoginRequest;
import com.example.demojpacache.dto.response.GetRefreshTokenResponse;
import com.example.demojpacache.dto.response.JwtResponse;
import com.example.demojpacache.exception.UserNotFoundException;
import com.example.demojpacache.security.JwtTokenUtil;
import com.example.demojpacache.security.UserSercurityImpl;
import com.example.demojpacache.service.base.AuthService;
import com.example.demojpacache.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserSercurityImpl user = (UserSercurityImpl) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateToken(user.getUsername());
        String refreshToken = UUID.randomUUID().toString();
        redisService.setWithTTL(refreshToken, user.getId().toString(), 600);
        return new JwtResponse(token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(), refreshToken);
    }

    @Override
    public GetRefreshTokenResponse refreshToken(String refreshToken) {
        String userId = (String) redisService.getValue(refreshToken).orElse(null);
        redisService.deleteKey(refreshToken);
        if (Objects.nonNull(userId)) {
            try {
                User user = userService.findById(Long.parseLong(userId));
                String tokenNew = jwtTokenUtil.generateToken(user.getUsername());
                String refreshTokenNew = UUID.randomUUID().toString();
                redisService.setWithTTL(refreshTokenNew, userId, 600);
                return new GetRefreshTokenResponse(tokenNew, refreshTokenNew);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
