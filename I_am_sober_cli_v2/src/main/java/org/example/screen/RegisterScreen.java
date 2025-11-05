package org.example.screen;

import com.example.client.ApiClient;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import com.example.util.UserValidator;
import org.example.util.InputValidator;

import java.io.IOException;
import java.util.Scanner;

public class RegisterScreen implements Screen{

    private Scanner scanner;
    private ApiClient apiClient;
    

    public RegisterScreen(Scanner scanner, ApiClient apiClient){
        this.scanner = scanner;
        this.apiClient = apiClient;
    }

    @Override
    public RoutingData init(){
        displayLabel();
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        boolean isPasswordValid = confirmPassword(password);
        if (!isPasswordValid){
            System.out.println("Invalid data. Please try again");
            return new RoutingData(Route.REGISTER);
        }
        return new RoutingData(handleRegistration(login, password)) ;

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

    private Route handleRegistration(String login, String password){
        try{
            apiClient.register(login, password);
            return Route.LOGIN;
        }
        catch (ApiResponseException e){
            System.out.println(e.getMessage());
            return Route.REGISTER;
        }
        catch(IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
            return Route.REGISTER;
        }


    }

}
