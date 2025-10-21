package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;
import com.example.util.UserValidator;
import org.example.util.InputValidator;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Scanner;

public class LoginScreen implements Screen{

    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;
    private Screen dashboardScreen;


    public LoginScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public Route init() {
        displayLabel();
        String login = getLoginFromUser();
        String password = getPasswordFromUser();

        Optional<HttpResponse<String>> response = apiClient.logIn(login, password);

        if (response.isEmpty()){
            System.out.println("Invalid data. Please try again");
            return Route.LOGIN;
        }

        if (promptRememberSession()){
            SessionTokenStore.saveToken(session.getToken());
        }
        return Route.DASHBOARD;

    }

    private Route loginAction(String login, String password){
        Optional<HttpResponse<String>> responseOptional = apiClient.logIn(login, password);
        if (responseOptional.isEmpty()){
            System.out.println("Invalid data. Please try again");
            return Route.LOGIN;
        }
        HttpResponse<String> response = responseOptional.get();
        if (response.statusCode() == 200){
            System.out.println("Login successfully");
            return Route.DASHBOARD;
        }
        System.out.println("Invalid data. Please try again");
        return Route.LOGIN;
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