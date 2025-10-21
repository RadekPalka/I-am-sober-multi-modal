package org.example.screen;

import com.example.dto.AddictionDto;
import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;

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
    public Route init()  {
        greet();
        addictionDtoList = apiClient.getPaginatedAddictions(session.getToken(), 0);
        showMenu();
        String option = askUserForOption();
        if (isNumeric(option)){
            int addictionNumber = Integer.parseInt(option);

        }
        else if(option.equalsIgnoreCase("l")){
            logout();
        }
        // TODO: This is temporary
        return Route.DASHBOARD;
    }

    private void greet(){
        System.out.println("Welcome " + session.getLogin());


    }

    private void showMenu(){
        if (addictionDtoList.isEmpty()){
            System.out.println("You have no addictions");
        }
        else{
            System.out.println("Your addiction:");
            for (int i = 0; i< addictionDtoList.size(); i++){
                AddictionDto addictionDto = addictionDtoList.get(i);
                System.out.printf("%d-> %s, daily cost: %.2f%n PLN%n", i+1, addictionDto.getName(), addictionDto.getCostPerDay());
            }
            System.out.println("Or press 'l' for logout");

        }
    }

    private String askUserForOption(){
        System.out.println("Type number of addiction to see a details:");
        return scanner.nextLine();
    }

    private boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private void logout(){
        String token = session.getToken();

        if(apiClient.logout(token)){
            SessionTokenStore.clearToken();
            session.clearUserCredentials();
            addictionDtoList.clear();


        }
    }


}
