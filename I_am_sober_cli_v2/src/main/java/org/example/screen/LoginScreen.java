package org.example.screen;

public class LoginScreen extends AbstractUserInputScreen{
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

  @Override
    public void init(){
        displayLabel();
        getLoginFromUser(instance);
        getPasswordFromUser(instance);
    }
}