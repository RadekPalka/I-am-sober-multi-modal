package org.example.screen;

import com.example.dto.AddictionDto;
import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;
import java.net.http.HttpResponse;
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
        showMenu();
        String option = askUserForOption();

        return checkUserOption(option);
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


        }
        System.out.println("l-> logout");
    }

    private String askUserForOption(){
        System.out.println("Choose your option");
        return scanner.nextLine();
    }

    private boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private boolean logout() throws InterruptedException {
        String token = session.getToken();
        for (int i= 0; i< 3; i++){
            if (i> 0){
               Thread.sleep(1000);
            }
            try{
                HttpResponse<String> response = apiClient.logout(token);
                int code = response.statusCode();
                if (code == 200){
                    System.out.println("Logout successfully");
                    SessionTokenStore.clearToken();
                    session.clearUserCredentials();
                    addictionDtoList.clear();
                    return true;
                }
                else{
                    // TODO handle specific status codes and write more specific messages
                    if (i ==2){
                        System.out.println("Something went wrong. Please try again");
                    }

                }
            } catch (Exception e) {
                if (i == 2){
                    System.out.println("No internet connection. Please try again");
                }

            }
        }
        return false;

    }

    private Route checkUserOption(String option)  {
        try{
            if (isNumeric(option)){
                int addictionNumber = Integer.parseInt(option);

                if (loadAddictions(addictionNumber -1)){
                    return Route.ADDICTION_DETAILS;
                }


            }
            else if(option.equalsIgnoreCase("l")){
                if (logout()){
                    return Route.HOME;
                }

            }
            System.out.println("Invalid data. Please try again.");
            return Route.DASHBOARD;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean loadAddictions(int index){
        AddictionDto addiction = addictionDtoList.get(index);
        try{
            HttpResponse<String> response = apiClient.getAddictionDetails(session.getToken(), addiction.getId());
            int code = response.statusCode();
            if (code == 200){
                System.out.println("Here are your addiction details");
                return true;
            }
            else{
                // TODO handle specific status codes and write more specific messages
                System.out.println("Something went wrong. Please try again");
            }
        } catch (Exception e) {
            System.out.println("No internet connection. Please try again");

        }
        return false;
    }


}
