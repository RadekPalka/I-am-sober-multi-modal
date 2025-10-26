package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.dto.TokenDto;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;
import com.example.util.UserValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.util.InputValidator;
import java.net.http.HttpResponse;
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

        boolean rememberMe = promptRememberSession();

        return loginAction(login, password, rememberMe);

    }

    private Route loginAction(String login, String password, boolean rememberMe){
        try{
            HttpResponse<String> response = apiClient.logIn(login, password);
            int code = response.statusCode();
            if (code == 200){
                System.out.println("Login Successfully");
                TokenDto tokenDto= apiClient.parseJson(response.body(), new TypeReference<TokenDto>() {});
                String token = tokenDto.getSessionToken();
                session.setToken(token);
                if (rememberMe){
                    SessionTokenStore.saveToken(token);
                }
                return Route.DASHBOARD;
            } else  {
                // TODO handle specific status codes and write more specific messages
                System.out.println("Something went wrong. Please try again");
                return Route.LOGIN;

            }
        } catch (Exception e) {
            System.out.println("No internet connection. Please try again");
            return Route.LOGIN;
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