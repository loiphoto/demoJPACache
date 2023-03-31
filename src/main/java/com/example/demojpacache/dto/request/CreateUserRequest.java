package com.example.demojpacache.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;

    private String password;

    private String phone;

    private String address;

    private String username;

    private Long roleId;
}
