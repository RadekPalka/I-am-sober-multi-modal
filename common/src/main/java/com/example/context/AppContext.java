package com.example.context;

import com.example.model.UserCredentials;

public class AppContext {
    private static final AppContext instance = new AppContext();
    private final UserCredentials credentials = new UserCredentials();

    private AppContext(){}

    public static AppContext getInstance(){
        return instance;
    }

    public UserCredentials getCredentials(){
        return credentials;
    }

    public void clearCredentials(){
        credentials.clear();
    }
}
