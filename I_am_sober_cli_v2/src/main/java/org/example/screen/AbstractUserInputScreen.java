package org.example.screen;

import com.example.context.AppContext;
import com.example.util.UserValidator;
import org.example.util.ScreenInputHelper;

import java.util.Scanner;

public abstract class AbstractUserInputScreen implements Screen{
    protected final Scanner scanner = new Scanner(System.in);
    protected String input;
    protected void getLoginFromUser(Screen currentScreen){
        do{
            System.out.print("Enter your login ( or press 'c' to cancel, or 'q' to quit to main menu ): ");
            input = ScreenInputHelper.readInput(scanner, currentScreen);
            if (!UserValidator.isValidLogin(input)){
                System.out.println("Login should have minimum five characters");
            }
        }while (!UserValidator.isValidLogin(input));
        AppContext.getInstance().getCredentials().setLogin(input);
    }

    protected void getPasswordFromUser(Screen currentScreen){
        do{
            System.out.print("Enter your password ( or press 'c' to cancel, or 'q' to quit to main menu ): ");
            input = ScreenInputHelper.readInput(scanner, currentScreen);
            if (!UserValidator.isValidPassword(input)){
                System.out.println("Password should have minimum seven characters, number and special character");
            }
        }while (!UserValidator.isValidPassword(input));
        AppContext.getInstance().getCredentials().setPassword(input);
    }
}
