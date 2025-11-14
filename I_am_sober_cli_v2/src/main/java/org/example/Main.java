package org.example;


import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.client.HttpClients;
import com.example.json.Jsons;
import com.example.routing.Route;
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
        ApiClient apiClient = new ApiClient(http, json);
        RoutingData routingData = new RoutingData();


        Screen registerScreen = new RegisterScreen(scanner, apiClient);
        Screen loginScreen = new LoginScreen(scanner, apiClient, session);
        Screen dashboard = new DashboardScreen(scanner, apiClient, session);
        Screen homeScreen = new HomeScreen(scanner);
        Screen addictionDetailsScreen = new AddictionDetailsScreen(apiClient, session, scanner);
        Screen addAddictionScreen = new AddAddictionScreen(scanner, apiClient, session);

        ScreenManager screenManager = new ScreenManager(session, apiClient, routingData);
        screenManager.register(Route.HOME, homeScreen);
        screenManager.register(Route.LOGIN, loginScreen);
        screenManager.register(Route.REGISTER, registerScreen);
        screenManager.register(Route.DASHBOARD, dashboard);
        screenManager.register(Route.ADDICTION_DETAILS, addictionDetailsScreen);
        screenManager.register(Route.ADD_ADDICTION, addAddictionScreen);

        screenManager.runApp();

    }
}