package org.example.core;

import com.example.auth.Session;
import com.example.client.ApiClient;

import com.example.dto.UserDto;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;

import org.example.screen.Screen;

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
        if (tokenOpt.isEmpty()) return Route.HOME;

        Optional<UserDto> userOpt = tokenOpt.flatMap(apiClient::fetchUser);
        if (userOpt.isEmpty()) return Route.HOME;

        UserDto user = userOpt.get();
        session.setLogin(user.getUsername());
        session.setToken(tokenOpt.get());
        return Route.DASHBOARD;
    }
}