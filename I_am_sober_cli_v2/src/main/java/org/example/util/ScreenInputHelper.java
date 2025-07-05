package org.example.util;

import com.example.context.AppContext;
import org.example.screen.HomeScreen;
import org.example.screen.Screen;

import java.util.Scanner;

public class ScreenInputHelper {
    public static void handleQuitOrCancel(String input, Screen screen){
        if (InputValidator.isCancelCommand(input)){
            AppContext.getInstance().clearCredentials();
            screen.init();

        }
        else if (InputValidator.isQuitCommand(input)){
            AppContext.getInstance().clearCredentials();
            HomeScreen.getInstance().init();
        }
    }

    public static String readInput(Scanner scanner, Screen screen){
        String input = scanner.nextLine().trim();
        handleQuitOrCancel(input, screen);
        return input;
    }
}
