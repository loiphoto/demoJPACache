package com.example.demojpacache.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
}
