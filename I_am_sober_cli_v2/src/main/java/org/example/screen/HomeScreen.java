package org.example.screen;

import org.example.util.InputValidator;

import java.util.Scanner;

public class HomeScreen implements Screen{
    private Scanner scanner;
    private String option;


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
