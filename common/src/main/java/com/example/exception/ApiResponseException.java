package com.example.exception;

public class ApiResponseException extends Exception {
    private final int statusCode;
    private final String responseBody;

    public ApiResponseException(int statusCode, String responseBody) {
        super("HTTP " + statusCode);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() { return statusCode; }

    public String getResponseBody() { return responseBody; }
}

