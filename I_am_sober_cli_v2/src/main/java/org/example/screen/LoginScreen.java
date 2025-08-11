package org.example.screen;

public class LoginScreen {
  private static LoginScreen instance;

  private LoginScreen(){}

  public static LoginScreen getInstance(){
    if (instance == null){
      instance = new LoginScreen();
    }
    return instance;
  }

  private void displayLabel(){
      System.out.println("Sing up");
  }


    public void init(){
        displayLabel();

    }
}