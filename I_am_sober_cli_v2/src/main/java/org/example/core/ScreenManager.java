package org.example.core;

import com.example.auth.Session;
import com.example.client.ApiClient;

import com.example.dto.UserDto;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;

import org.example.screen.AddictionDetailsScreen;
import org.example.screen.BasicRoutingData;
import org.example.screen.Screen;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ScreenManager {
    private final Map<Route, Screen> screens = new EnumMap<>(Route.class);
    private final Session session;
    private final ApiClient apiClient;
    private final BasicRoutingData basicRoutingData;

    public ScreenManager(Session session, ApiClient apiClient, BasicRoutingData basicRoutingData) {
        this.session = session;
        this.apiClient = apiClient;
        this.basicRoutingData = basicRoutingData;
    }

    public void register(Route route, Screen screen) {
        screens.put(route, screen);
    }

    public void runApp() {
        System.out.println("Welcome in I am sober app");
        basicRoutingData.setRoute(decideInitialRoute());
        System.out.println(basicRoutingData.getRoute());

        while (!basicRoutingData.shouldExitApp()) {
            Screen screen = screens.get(basicRoutingData.getRoute());
            if (screen == null) {
                System.out.println("No screen registered for route: " + basicRoutingData.getRoute());
                break;
            }
            if (screen instanceof AddictionDetailsScreen){
                ((AddictionDetailsScreen) screen).setId(basicRoutingData.getAddictionDetailsId());
            }
            basicRoutingData.setRoute(screen.init().getRoute());


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
            session.setLoginAndToken(userDto.getUsername(), token);
            return Route.DASHBOARD;
        }
        catch (ApiResponseException e) {
            if (e.getStatusCode() == 401){
                SessionTokenStore.clearToken();
            }
            System.out.println(e.getMessage());
            return Route.HOME;
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
            return Route.HOME;
        }


    }
}