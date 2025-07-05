package org.example.util;

public class InputValidator {
    public static boolean isValidMainMenuOption(String input){
        return input.equalsIgnoreCase("r") || input.equalsIgnoreCase("l") || input.equalsIgnoreCase("e");
    }

    public static boolean isQuitCommand(String input){
        return input.equalsIgnoreCase("q");
    }

    public static boolean isCancelCommand(String input){
        return input.equalsIgnoreCase("c");
    }
}
