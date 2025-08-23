package org.example.screen;

import org.example.util.InputValidator;
import org.example.util.ScreenManager;

import java.util.Scanner;

public class HomeScreen implements Screen{
    private Scanner scanner;
    private Screen registerScreen;
    private Screen loginScreen;


    public HomeScreen(Scanner scanner, Screen registerScreen, Screen loginScreen){

        this.scanner = scanner;
        this.registerScreen = registerScreen;
        this.loginScreen = loginScreen;
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
        String option;
        do{
            option = scanner.nextLine().trim();
            if (!InputValidator.isValidMainMenuOption(option)){
                System.out.println("Choose correct option");
                showMenu();
            }
        }while(!InputValidator.isValidMainMenuOption(option));
        return option;
    }

    private void checkUserOption(String option){
        switch (option.toLowerCase().trim()){
            case "r":
                ScreenManager.initScreen(registerScreen);
                break;
            case "l":
                ScreenManager.initScreen(loginScreen);
                break;
            case "e":
                System.exit(0);
                break;

        }
    }



    @Override
    public void init(){
        displayGreeting();
        showMenu();
        String option = getOptionFromUser();
        checkUserOption(option);
    }
}
