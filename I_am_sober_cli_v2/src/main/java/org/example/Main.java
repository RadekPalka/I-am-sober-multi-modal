package org.example;


import org.example.screen.HomeScreen;
import org.example.screen.RegisterScreen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RegisterScreen registerScreen = new RegisterScreen();
        Scanner scanner = new Scanner(System.in);
        HomeScreen homeScreen = new HomeScreen(scanner);
        homeScreen.init();

    }
}