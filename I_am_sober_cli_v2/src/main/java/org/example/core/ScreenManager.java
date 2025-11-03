package org.example.core;

import com.example.auth.Session;
import com.example.client.ApiClient;

import com.example.dto.UserDto;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;

import org.example.screen.Screen;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ScreenManager {
    private final Map<Route, Screen> screens = new EnumMap<>(Route.class);
    private Session session;
    private ApiClient apiClient;

    public ScreenManager(Session session, ApiClient apiClient) {

        this.session = session;
        this.apiClient = apiClient;
    }

    public void register(Route route, Screen screen) {
        screens.put(route, screen);
    }

    public void runApp() {
        System.out.println("Welcome in I am sober app");
        Route route = decideInitialRoute();

        while (route != Route.EXIT) {
            Screen screen = screens.get(route);
            if (screen == null) {
                System.out.println("No screen registered for route: " + route);
                break;
            }
            route = screen.init();
        }
        System.out.println("Bye!");
    }

    private Route decideInitialRoute() {
        Optional<String> tokenOpt = SessionTokenStore.loadToken();
        if (tokenOpt.isEmpty()) {
            return Route.HOME;
        }
        try{
            System.out.println("Loading data, please wait");
            String token = tokenOpt.get();
            UserDto userDto = apiClient.fetchUser(token);
            session.setLogin(userDto.getUsername());
            session.setToken(token);
            return Route.DASHBOARD;
        }
        catch (ApiResponseException e) {
            System.out.println(e.getMessage());
            return Route.HOME;
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
            return Route.HOME;
        }


    }
}