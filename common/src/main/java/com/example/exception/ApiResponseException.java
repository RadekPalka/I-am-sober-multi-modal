package com.example.exception;

import java.util.Map;

public class ApiResponseException extends Exception {
    private final int statusCode;
    private final String message;


    public ApiResponseException(int statusCode) {
        this.statusCode = statusCode;
        Map<Integer, String> STATUS_MESSAGES = Map.of(
                400, "Invalid data. Please check your input.",
                401, "Unauthorized. Please log in again.",
                403, "Access denied.",
                404, "Resource not found.",
                500, "Internal server error. Try again later."
        );
        this.message = STATUS_MESSAGES.get(statusCode);
    }

    public String getMessage() { return message; }
}
