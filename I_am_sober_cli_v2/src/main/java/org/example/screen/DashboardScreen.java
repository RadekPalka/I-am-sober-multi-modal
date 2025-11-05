package org.example.screen;

import com.example.dto.AddictionDto;
import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;
import org.example.util.InputValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DashboardScreen implements Screen{
    private final Scanner scanner;
    private final ApiClient apiClient;
    private final Session session;
    private List<AddictionDto> addictionDtoList =  new ArrayList<>();
    private int pageNumber = 0;


    public DashboardScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public RoutingData init()  {
        greet();
        if (addictionDtoList.isEmpty()){
            loadAddictions();
        }

        showMenu();
        String option = askUserForOption();

        return checkUserOption(option);
    }



    private void greet(){
        System.out.println("Welcome " + session.getLogin());


    }

    private void loadAddictions(){
        String token = session.getToken();
        try{
            addictionDtoList = apiClient.getPaginatedAddictions(token, pageNumber);
            pageNumber ++;
        }
        catch (ApiResponseException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
        }

    }

    private boolean isMoreAddictionsAvailable(){
        int size = addictionDtoList.size();
        return size != 0 && size % 10 == 0;
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


        }
        if (isMoreAddictionsAvailable()){
            System.out.println("m-> more load more addictions");
        }
        System.out.println("l-> logout");
        System.out.println("q-> quit");
    }

    private String askUserForOption(){
        System.out.println("Choose your option");
        return scanner.nextLine();
    }

    private boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private RoutingData handleLogout() throws InterruptedException {
        String token = session.getToken();
        for (int i = 0; i< 3; i++){
            if (i > 0){
               Thread.sleep(1000);
            }
            try {
                apiClient.logout(token);
                break;
            }
            catch (ApiResponseException e) {
                if (i == 2){
                    System.out.println(e.getMessage());
                }

            }
            catch (IOException | InterruptedException e){
                if (i == 2){
                    System.out.println("Network error. Please check your connection.");
                }

            }


        }
        SessionTokenStore.clearToken();
        session.clearUserCredentials();
        addictionDtoList.clear();
        System.out.println("Logout successfully");
        return new RoutingData(Route.HOME);

    }

    private RoutingData checkUserOption(String option)  {
        try{
            if (isNumeric(option)){
                int addictionIndex = Integer.parseInt(option) -1;
                if (isIndexInRange(addictionIndex)){
                    long id = getAddictionId(addictionIndex);
                    return new RoutingData(Route.ADDICTION_DETAILS, id);
                }

            }
            else if(option.equalsIgnoreCase("l")){
                return handleLogout();

            } else if (isMoreAddictionsAvailable() && option.equalsIgnoreCase("m")) {
                loadAddictions();
                return new RoutingData(Route.DASHBOARD);
            }
            else if (InputValidator.isQuitCommand(option)){
                return new RoutingData(Route.EXIT);
            }
            System.out.println("Invalid data. Please try again.");
            return new RoutingData(Route.DASHBOARD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isIndexInRange(int index){
        return addictionDtoList.size() > index && index >=0;
    }

    private long getAddictionId(int addictionIndex){
        return addictionDtoList.get(addictionIndex).getId();
    }
}
