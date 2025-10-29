package org.example;


import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.client.HttpClients;
import com.example.json.Jsons;
import com.example.routing.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.context.UiContext;
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
        UiContext uiContext = new UiContext();

        Screen registerScreen = new RegisterScreen(scanner, apiClient);
        Screen loginScreen = new LoginScreen(scanner, apiClient, session);
        Screen dashboard = new DashboardScreen(scanner, apiClient, session, uiContext);
        Screen homeScreen = new HomeScreen(scanner);
        Screen addictionDetailsScreen = new AddictionDetailsScreen(uiContext, apiClient, session, scanner);

        ScreenManager screenManager = new ScreenManager(session, apiClient);
        screenManager.register(Route.HOME, homeScreen);
        screenManager.register(Route.LOGIN, loginScreen);
        screenManager.register(Route.REGISTER, registerScreen);
        screenManager.register(Route.DASHBOARD, dashboard);
        screenManager.register(Route.ADDICTION_DETAILS, addictionDetailsScreen);

        screenManager.runApp();

    }
}