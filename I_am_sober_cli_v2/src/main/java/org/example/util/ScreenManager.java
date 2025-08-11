package org.example.util;

import org.example.screen.LoginScreen;
import org.example.screen.RegisterScreen;
import org.example.screen.Screen;

public class ScreenManager {
    private RegisterScreen registerScreen;
    private LoginScreen loginScreen;

    public ScreenManager(RegisterScreen registerScreen, LoginScreen loginScreen){
        this.registerScreen = registerScreen;
        this.loginScreen = loginScreen;
    }

    public void intScreen(Screen screen){
        screen.init();
    }
}
