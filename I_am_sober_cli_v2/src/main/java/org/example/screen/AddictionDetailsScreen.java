package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.dto.AddictionDetailsDto;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import java.io.IOException;
import java.util.Scanner;

public class AddictionDetailsScreen implements Screen{
    private AddictionDetailsDto addictionDetailsDto;
    private ApiClient apiClient;
    private Session session;
    private Scanner scanner;
    private Long id;

    public AddictionDetailsScreen(ApiClient apiClient, Session session, Scanner scanner){

        this.apiClient = apiClient;
        this.session = session;
        this.scanner = scanner;
    }
    @Override
    public RoutingData init() {
        loadAddictionDetails();
        id = null;
        return new RoutingData(Route.DASHBOARD);
    }

    public void setId(Long id){
        this.id = id;
    }
    public void test(){}

    private void loadAddictionDetails(){
        try{
            addictionDetailsDto = apiClient.getAddictionDetails(session.getToken(), id);
            System.out.println(addictionDetailsDto.toString());
            System.out.println("Press any key to return to Dashboard");
            scanner.nextLine();

        }
        catch (ApiResponseException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
        }

    }
}
