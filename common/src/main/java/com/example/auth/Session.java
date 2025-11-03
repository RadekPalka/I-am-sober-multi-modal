package com.example.auth;

public class Session {
    private String token;
    private String login;


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
