package org.example.screen;

public class RegisterScreen {
    private static RegisterScreen instance;
    

    private RegisterScreen(){}


    public static RegisterScreen getInstance(){
        if (instance == null){
            instance = new RegisterScreen();
        }

        return instance;
    }

    private void displayLabel(){
        System.out.println("Sing in");
    }

    private void confirmPassword(){


    }


    public void init(){
        displayLabel();

        confirmPassword();
    }

}
