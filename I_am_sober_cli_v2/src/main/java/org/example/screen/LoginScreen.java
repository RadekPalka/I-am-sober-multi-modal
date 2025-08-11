package org.example.screen;

import java.util.Scanner;

public class LoginScreen implements Screen{
    private String login;
    private String password;
    private String userInput;
    private Scanner scanner;


    public LoginScreen(Scanner scanner){
        this.scanner = scanner;
    }



  private void displayLabel(){
      System.out.println("Sing up");
  }


    public void init(){
        displayLabel();

    }
}