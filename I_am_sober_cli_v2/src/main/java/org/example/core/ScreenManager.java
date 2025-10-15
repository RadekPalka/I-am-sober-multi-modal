package org.example.core;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.service.SessionTokenStore;
import org.example.screen.Screen;

import java.util.Optional;

public class ScreenManager {
    private Screen homeScreen;
    private Screen dashboardScreen;
    private Session session;
    private ApiClient apiClient;

    public ScreenManager(Screen homeScreen, Screen dashboardScreen, Session session, ApiClient apiClient) {
        this.homeScreen = homeScreen;
        this.dashboardScreen = dashboardScreen;
        this.session = session;
        this.apiClient = apiClient;
    }

    public void decideInitialScreen() {
        Optional<String> tokenOpt = SessionTokenStore.loadToken();

        if (tokenOpt.isPresent()) {
            tokenOpt.flatMap(apiClient::fetchUser)
                    .ifPresentOrElse(
                            user -> {
                                session.setLogin(user.getUsername());
                                session.setToken(tokenOpt.get());
                                dashboardScreen.init();
                            },
                            () -> homeScreen.init()
                    );
        }
        else{
            homeScreen.init();
        }


    }
}