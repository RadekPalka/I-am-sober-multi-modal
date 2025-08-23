package org.example;


import com.example.auth.Session;
import com.example.client.ApiClient;
import org.example.screen.HomeScreen;
import org.example.screen.LoginScreen;
import org.example.screen.RegisterScreen;
import org.example.screen.Screen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Session session = new Session();
        ApiClient apiClient = new ApiClient(session);
        Screen registerScreen = new RegisterScreen(scanner, apiClient, session);
        Screen loginScreen = new LoginScreen(scanner, apiClient, session);


        HomeScreen homeScreen = new HomeScreen(scanner, registerScreen, loginScreen);

        homeScreen.init();

    }
}