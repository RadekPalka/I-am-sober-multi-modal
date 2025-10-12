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
        String option = askUserForOption();
        if (isNumeric(option)){
            int addictionNumber = Integer.parseInt(option);

        }
    }

    private void greet(){
        System.out.println("Welcome " + session.getLogin());


    }

    private void printAddictions(){
        if (addictionDtoList.isEmpty()){
            System.out.println("You have no addictions");
        }
        else{
            System.out.println("Your addiction:");
            for (int i = 0; i< addictionDtoList.size(); i++){
                AddictionDto addictionDto = addictionDtoList.get(i);
                System.out.printf("%d-> %s, daily cost: %.2f%n PLN%n", i+1, addictionDto.getName(), addictionDto.getCostPerDay());
            }

        }
    }

    private String askUserForOption(){
        System.out.println("Type number of addiction to see a details:");
        return scanner.nextLine();
    }

    public static boolean isNumeric(String input) {
        return input.matches("\\d+");
    }




}
