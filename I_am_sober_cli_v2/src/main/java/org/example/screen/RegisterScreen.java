package org.example.screen;

import com.example.util.UserValidator;

import java.util.Scanner;

public class RegisterScreen implements Screen{
    private String login;
    private String password;
    private String userInput;
    private Scanner scanner;
    

    public RegisterScreen(Scanner scanner){
        this.scanner = scanner;
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
            if (UserValidator.isValidPassword(userInput)){
                password = userInput;
                break;
            }
            System.out.println("Your password is incorrect");

        }
    }

    private void displayLabel(){
        System.out.println("Sing in");
    }

    private void confirmPassword(){
        while (true){
            System.out.print("Confirm your password: ");
            userInput = scanner.nextLine();
            if (password.equals(userInput)){

                break;
            }
            System.out.println("Passwords do not match. Please try again.");

        }

    }


    public void init(){
        displayLabel();
        getLoginFromUser();
        getPasswordFromUser();
        confirmPassword();
    }

}
