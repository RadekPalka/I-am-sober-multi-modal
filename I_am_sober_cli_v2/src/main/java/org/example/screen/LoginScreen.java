package org.example.screen;

import com.example.client.ApiClient;
import com.example.global.Global;
import com.example.util.UserValidator;
import org.example.util.InputValidator;

import java.util.Scanner;

public class LoginScreen implements Screen{

    private Scanner scanner;
    private ApiClient apiClient;


    public LoginScreen(Scanner scanner, ApiClient apiClient){
        this.scanner = scanner;
        this.apiClient = apiClient;
    }

    public void init(){
        displayLabel();
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        if (login != null && password != null){
            apiClient.logIn(login, password);
        }

    }

  private void displayLabel(){
      System.out.println("Sing up");
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

}