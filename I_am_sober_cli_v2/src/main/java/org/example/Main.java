package org.example;


import org.example.screen.HomeScreen;
import org.example.screen.LoginScreen;
import org.example.screen.RegisterScreen;
import org.example.screen.Screen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Screen registerScreen = new RegisterScreen(scanner);
        Screen loginScreen = new LoginScreen(scanner);

        HomeScreen homeScreen = new HomeScreen(scanner, registerScreen, loginScreen);

        homeScreen.init();

    }
}