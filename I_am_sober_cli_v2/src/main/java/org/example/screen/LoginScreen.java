package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.global.Global;
import com.example.util.UserValidator;
import org.example.util.InputValidator;

import java.util.Scanner;

public class LoginScreen implements Screen{

    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;


    public LoginScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
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
            System.out.println("Your must have at least five characters");

        }
    }

    private boolean promptRememberSession(){
        System.out.print("Remember me y/n (default n):");
         String input = scanner.nextLine();
        if (InputValidator.isQuitCommand(input)){
            System.exit(0);
        }
        else if(input.equalsIgnoreCase("y")){
            return true;
        }
        else if(!input.equalsIgnoreCase("n")){
            System.out.println("Wrong character. I am staying with default value");
        }

        return false;
    }



}