package org.example;


import org.example.screen.HomeScreen;
import org.example.screen.LoginScreen;
import org.example.screen.RegisterScreen;
import org.example.screen.Screen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Screen registerScreen = new RegisterScreen();
        Screen loginScreen = new LoginScreen();
        Scanner scanner = new Scanner(System.in);
        HomeScreen homeScreen = new HomeScreen(scanner, registerScreen, loginScreen);

        homeScreen.init();

    }
}