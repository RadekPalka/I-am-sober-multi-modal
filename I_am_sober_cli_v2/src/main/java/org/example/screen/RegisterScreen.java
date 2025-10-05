package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.global.Global;
import com.example.util.UserValidator;
import org.example.util.InputValidator;

import java.util.Scanner;

public class RegisterScreen implements Screen{

    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;
    

    public RegisterScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public void init(){
        displayLabel();
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        boolean isPasswordValid = confirmPassword(password);
        if (isPasswordValid){
            apiClient.register(login, password);
        }

    }

    private String getLoginFromUser(){
        while (true){
            System.out.print("Enter your login: ");
            String input = scanner.nextLine();
            if (UserValidator.isUserInputValid(input)){
                return input;
            }
            System.out.println("Your login must have at least five characters");

        }

    }

    private String getPasswordFromUser(){
        while (true){
            System.out.print("Enter your password: ");
            String input = scanner.nextLine();
            if (InputValidator.isQuitCommand(input)){
                System.exit(0);
            }
            else if (UserValidator.isUserInputValid(input)){
                return input;
            }
            System.out.println("Your password must have at least five characters");

        }
    }

    private void displayLabel(){
        System.out.println("Sing in");
    }

    private boolean confirmPassword(String password){
        String userInput;
        while (true){
            System.out.print("Confirm your password: ");
            userInput = scanner.nextLine();
            if (InputValidator.isQuitCommand(userInput)){
                System.exit(0);
            }
            else if (password.equals(userInput)){

                return true;
            }
            System.out.println("Passwords do not match. Please try again.");

        }

    }

}
