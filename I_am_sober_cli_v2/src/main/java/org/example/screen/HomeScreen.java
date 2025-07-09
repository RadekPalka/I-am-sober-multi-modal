package org.example.screen;

import org.example.util.InputValidator;

import java.util.Scanner;

public class HomeScreen implements Screen{
    private static HomeScreen instance;
    private Scanner scanner;
    private String option;
    private HomeScreen(){
        scanner = new Scanner(System.in);
    }

    private HomeScreen(Scanner scanner){
        this.scanner = scanner;
    }

    public static HomeScreen getInstance(){
        if (instance == null){
            instance = new HomeScreen();
        }
        return instance;
    }

    public static HomeScreen getInstance(Scanner scanner){
        if (instance == null){
            instance = new HomeScreen(scanner);
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

    public static void resetInstance(){
        instance = null;
    }

    @Override
    public void init(){
        displayGreeting();
        showMenu();
        getOptionFromUser();
        checkUserOption();
    }
}
