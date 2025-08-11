package org.example;


import org.example.screen.HomeScreen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HomeScreen homeScreen = new HomeScreen(scanner);
        homeScreen.init();

    }
}