package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;

import java.util.Scanner;

public class Dashboard implements Screen{
    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;

    public Dashboard(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public void init(){
        greet();
    }

    private void greet(){
        System.out.println("Welcome " + session.getLogin());
    }
}
