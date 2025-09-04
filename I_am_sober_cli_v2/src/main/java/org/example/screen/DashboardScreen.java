package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;

import java.util.Scanner;

public class DashboardScreen implements Screen{
    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;

    public DashboardScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public void init()  {
        greet();
        apiClient.getPaginatedAddictions(session.getToken(), 0);

    }

    private void greet(){
        System.out.println("Welcome " + session.getLogin());
        System.out.println("Choose your option");

    }


}
