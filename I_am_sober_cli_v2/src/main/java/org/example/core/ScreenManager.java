package org.example.core;

import com.example.auth.Session;
import com.example.service.SessionTokenStore;
import org.example.screen.DashboardScreen;
import org.example.screen.HomeScreen;
import java.util.Optional;

public class ScreenManager {
    private HomeScreen homescreen;
    private DashboardScreen dashboardScreen;
    private Session session;

    public ScreenManager(HomeScreen homeScreen, DashboardScreen dashboardScreen, Session session){
        this.homescreen = homeScreen;
        this.dashboardScreen = dashboardScreen;
        this.session = session;
    }

    public String loadTokenIfExists(){
        Optional<String> token = SessionTokenStore.loadToken();
        token.ifPresent(session::setToken);

    }

}
