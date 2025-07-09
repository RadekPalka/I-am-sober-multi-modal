package org.example.screen;

import com.example.context.AppContext;
import org.example.util.ScreenInputHelper;

public class RegisterScreen extends AbstractUserInputScreen{
    private static RegisterScreen instance;
    

    private RegisterScreen(){}


    public static RegisterScreen getInstance(){
        if (instance == null){
            instance = new RegisterScreen();
        }

        return instance;
    }

    private void displayLabel(){
        System.out.println("Sing in");
    }

    private void confirmPassword(){
        String password = AppContext.getInstance().getCredentials().getPassword();
        do{
            System.out.print("Confirm your password ( or press 'c' to cancel, or 'q' to quit to main menu ): ");
            input = ScreenInputHelper.readInput(scanner, instance);

            if (!password.equals(input)){
                System.out.println("Passwords do not match");
            }
        }while (!password.equals(input.trim()));

    }

    @Override
    public void init(){
        displayLabel();
        getLoginFromUser(instance);
        getPasswordFromUser(instance);
        confirmPassword();
    }

}
