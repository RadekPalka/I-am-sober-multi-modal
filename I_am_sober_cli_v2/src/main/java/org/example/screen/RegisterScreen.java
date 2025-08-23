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
        if (login != null && password != null && isPasswordValid){
            apiClient.register(login, password);
        }

    }

    private String getLoginFromUser(){
        String userInput;
        while (true){
            System.out.print("Enter your login: ");
            userInput = scanner.nextLine();
            if (UserValidator.isValidLogin(userInput)){
                return userInput;
            }
            System.out.println("Your login is incorrect");

        }

    }

    private String getPasswordFromUser(){
        String userInput;
        while (true){
            System.out.print("Enter your password: ");
            userInput = scanner.nextLine();
            if (InputValidator.isQuitCommand(userInput)){
                System.exit(0);
            }
            else if (UserValidator.isValidPassword(userInput)){
                return userInput;
            }
            System.out.println("Your password is incorrect");

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
