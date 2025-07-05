package com.example.util;

public class UserValidator {
    public static boolean isValidLogin(String login){
        return login.length() > 4;
    }

    public static boolean isValidPassword(String password){
        return password.length() > 7 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*") &&
                password.matches(".*[a-zA-Z].*");
    }
}

