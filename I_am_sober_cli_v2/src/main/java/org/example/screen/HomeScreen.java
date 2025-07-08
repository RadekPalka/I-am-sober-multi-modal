package org.example.screen;

import org.example.util.InputValidator;

import java.util.Scanner;

public class HomeScreen implements Screen{
    private static HomeScreen instance;
    private final Scanner scanner = new Scanner(System.in);
    private String option;
    private HomeScreen(){}

    public static HomeScreen getInstance(){
        if (instance == null){
            instance = new HomeScreen();
        }
        return instance;
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


    private void getOptionFromUser(){
        do{
            option = scanner.nextLine().trim();
            if (!InputValidator.isValidMainMenuOption(option)){
                System.out.println("Choose correct option");
                showMenu();
            }
        }while(!InputValidator.isValidMainMenuOption(option));

    }

    private void checkUserOption(){
        switch (option.toLowerCase().trim()){
            case "r":
                RegisterScreen.getInstance().init();
                break;
            case "l":
                LoginScreen.getInstance().init();
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
        getOptionFromUser();
        checkUserOption();
    }
}
