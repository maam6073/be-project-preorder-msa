package com.dohyeong.activity_service.global.exception;

public class ErrorResponse {
    private int status;
    private String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
