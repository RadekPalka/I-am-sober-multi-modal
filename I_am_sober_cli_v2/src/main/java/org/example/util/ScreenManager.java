package org.example.util;

import org.example.screen.Screen;

public class ScreenManager {
    private ScreenManager(){}

    public static void initScreen(Screen screen){
        screen.init();
    }
}
