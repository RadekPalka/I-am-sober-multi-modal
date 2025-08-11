package org.example.screen;

import com.example.client.ApiClient;
import com.example.global.Global;
import com.example.util.UserValidator;
import org.example.util.InputValidator;

import java.util.Scanner;

public class LoginScreen implements Screen{
    private String login;
    private String password;
    private String userInput;
    private Scanner scanner;
    private ApiClient apiClient;


    public LoginScreen(Scanner scanner, ApiClient apiClient){
        this.scanner = scanner;
        this.apiClient = apiClient;
    }



  private void displayLabel(){
      System.out.println("Sing up");
  }

    private void getLoginFromUser(){
        while (true){
            System.out.print("Enter your login: ");
            userInput = scanner.nextLine();
            if (UserValidator.isValidLogin(userInput)){
                login = userInput;
                break;
            }
            System.out.println("Your login is incorrect");

        }

    }

    private void getPasswordFromUser(){
        while (true){
            System.out.print("Enter your password: ");
            userInput = scanner.nextLine();
            if (InputValidator.isQuitCommand(userInput)){
                System.exit(0);
            }
            else if (UserValidator.isValidPassword(userInput)){
                password = userInput;
                break;
            }
            System.out.println("Your password is incorrect");

        }
    }

    private void clearUserData(){
        login = null;
        password = null;
    }

    private void sendDataToApi(){
        String json = String.format("""
    {
      "username": "%s",
      "password": "%s"
    }
    """, login, password);
        try {
            String response = apiClient.post(Global.loginUrl, json);
            System.out.println("Response from API: " + response);
        } catch (Exception e) {
            System.out.println("API call failed: " + e.getMessage());
        }
    }

    public void init(){
        displayLabel();
        getLoginFromUser();
        getPasswordFromUser();
        sendDataToApi();

    }
}