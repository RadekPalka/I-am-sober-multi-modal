package com.example.auth;

public class Session {
    private String token;
    private String login;
    private boolean shouldReloadAddictions = true;

    public boolean shouldReloadAddictions(){
        return shouldReloadAddictions;
    }

    public void markAddictionsForReload() {
        shouldReloadAddictions = true;
    }

    public void clearAddictionsReloadFlag() {
        shouldReloadAddictions = false;
    }


    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }
    public void setLoginAndToken(String login, String token){
        this.login = login;
        this.token = token;
    }



    public void clearUserCredentials(){
        login = null;
        token = null;
    }
}
