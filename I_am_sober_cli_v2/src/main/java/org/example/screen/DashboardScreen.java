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

enum DashboardMode {
    NORMAL,
    LOAD_ERROR
}

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
        System.out.println("Welcome " + session.getLogin());
        boolean isAddictionsLoaded = true;
        DashboardMode mode = DashboardMode.NORMAL;
        if (session.shouldReloadAddictions()){
            isAddictionsLoaded = loadAddictions();
        }
        if(!isAddictionsLoaded){
            mode = DashboardMode.LOAD_ERROR;
        }
        showMenu(mode);
        String option = askUserForOption();

        return checkUserOption(option, mode);
    }


    private boolean loadAddictions(){
        String token = session.getToken();
        try{
            ArrayList<AddictionDto> page = apiClient.getPaginatedAddictions(token, pageNumber);
            addictionDtoList.addAll(page);
            pageNumber ++;
            session.clearAddictionsReloadFlag();
            return true;
        }
        catch (ApiResponseException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
            return false;
        }

    }

    private boolean isMoreAddictionsAvailable(){
        int size = addictionDtoList.size();
        return size != 0 && size % 10 == 0;
    }

    private void showMenu(DashboardMode mode){
        if (mode == DashboardMode.NORMAL){
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
            System.out.println("a-> Add new addiction");
            System.out.println("l-> logout");
            System.out.println("q-> quit");
        } else if (mode == DashboardMode.LOAD_ERROR) {
            System.out.println("l-> logout");
            System.out.println("q-> quit");
            System.out.println("Enything else -> retry");
        }

    }

    private String askUserForOption(){
        System.out.println("Choose your option");
        return scanner.nextLine();
    }

    private boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private RoutingData handleLogout() {
        String token = session.getToken();
        for (int i = 0; i< 3; i++){
            try {
                if (i > 0){
                    Thread.sleep(1000);
                }
                apiClient.logout(token);
                break;
            }
            catch (ApiResponseException e) {
                if (i == 2){
                    System.out.println(e.getMessage());
                }

            }
            catch (IOException | InterruptedException e){
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
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

    private RoutingData checkUserOption(String option, DashboardMode mode)  {
        if (mode == DashboardMode.NORMAL){
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
            else if (option.equalsIgnoreCase("a")){
                return new RoutingData(Route.ADD_ADDICTION);
            }
            else if (InputValidator.isQuitCommand(option)){
                return new RoutingData(Route.EXIT);
            }
            System.out.println("Invalid data. Please try again.");
            return new RoutingData(Route.DASHBOARD);


        } else if (mode == DashboardMode.LOAD_ERROR) {
           if (InputValidator.isQuitCommand(option)){
                return new RoutingData(Route.EXIT);
            }else if(option.equalsIgnoreCase("l")) {
               return handleLogout();
            }
            else{
                   loadAddictions();
                   return new RoutingData(Route.DASHBOARD);
               }

            }
        return new RoutingData(Route.DASHBOARD);

        }




    private boolean isIndexInRange(int index){
        return addictionDtoList.size() > index && index >=0;
    }

    private long getAddictionId(int addictionIndex){
        return addictionDtoList.get(addictionIndex).getId();
    }
}
