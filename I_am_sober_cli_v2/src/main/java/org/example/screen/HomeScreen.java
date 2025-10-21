package org.example.screen;

import com.example.routing.Route;
import org.example.util.InputValidator;

import java.io.IOException;
import java.util.Scanner;

public class HomeScreen implements Screen{
    private Scanner scanner;


    public HomeScreen(Scanner scanner){

        this.scanner = scanner;
    }



    private void displayGreeting(){
        System.out.println("Welcome to I AM SOBER");
        System.out.println("Chose your option");
    }

    private void showMenu(){
        System.out.println("r -> Register");
        System.out.println("l -> Login");
        System.out.println("e -> exit");
    }


    private String getOptionFromUser(){
        String input;
        do{
            input = scanner.nextLine().trim();
            if (!InputValidator.isValidMainMenuOption(input)){
                System.out.println("Choose correct input");
                showMenu();
            }
        }while(!InputValidator.isValidMainMenuOption(input));
        return input;
    }

    private Route checkUserOption(String option) {
        switch (option.toLowerCase().trim()){
            case "r":
                return Route.REGISTER;
            case "l":
                return Route.LOGIN;
            case "e":
                return Route.EXIT;
            default:
                return Route.HOME;
        }
    }



    @Override
    public Route init() {
        displayGreeting();
        showMenu();
        String option = getOptionFromUser();
        return checkUserOption(option);
    }
}
