package com.example.demojpacache.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRefreshTokenResponse {
    private String accessToken;

    private String refreshToken;
}
