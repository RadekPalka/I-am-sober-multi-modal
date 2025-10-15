package org.example;


import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.client.HttpClients;
import com.example.json.Jsons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.ScreenManager;
import org.example.screen.*;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Session session = new Session();
        HttpClient http = HttpClients.defaultClient();
        ObjectMapper json = Jsons.defaultMapper();


        ApiClient apiClient = new ApiClient(session, http, json);

        Screen registerScreen = new RegisterScreen(scanner, apiClient, session);
        Screen loginScreen = new LoginScreen(scanner, apiClient, session);
        Screen dashboard = new DashboardScreen(scanner, apiClient, session);
        Screen homeScreen = new HomeScreen(scanner, registerScreen, loginScreen);

        ScreenManager screenManager = new ScreenManager(homeScreen, dashboard, session, apiClient);
        screenManager.decideInitialScreen();

    }
}