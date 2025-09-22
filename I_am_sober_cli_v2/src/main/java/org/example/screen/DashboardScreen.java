package org.example.screen;

import com.example.addictions.dto.AddictionDto;
import com.example.auth.Session;
import com.example.client.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DashboardScreen implements Screen{
    private Scanner scanner;
    private ApiClient apiClient;
    private Session session;
    private List<AddictionDto> addictionDtoList =  new ArrayList<>();

    public DashboardScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public void init()  {
        greet();
        addictionDtoList = apiClient.getPaginatedAddictions(session.getToken(), 0);
        printAddictions();

    }

    private void greet(){
        System.out.println("Welcome " + session.getLogin());
        System.out.println("Choose your option");

    }

    private void printAddictions(){
        if (addictionDtoList.isEmpty()){
            System.out.println("You have no addictions");
        }
        else{
            for (int i = 0; i< addictionDtoList.size(); i++){
                AddictionDto addictionDto = addictionDtoList.get(i);
                System.out.printf("%d-> %s, daily cost: %.2f%n PLN", i+1, addictionDto.getName(), addictionDto.getCostPerDay());
            }
        }
    }


}
