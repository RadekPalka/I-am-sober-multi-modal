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

    private void loadAddictionDetails(){
        try{
            addictionDetailsDto = apiClient.getAddictionDetails(session.getToken(), id);
            System.out.println(addictionDetailsDto.toString());

        }
        catch (ApiResponseException e) {
            System.out.println(errorMessageForAddictionDetails(e));
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
        }

    }

    private String errorMessageForAddictionDetails(ApiResponseException e) {
        int status = e.getStatusCode();

        return switch (status) {
            case 400 -> "Invalid addiction id. Please try again.";
            case 401 -> "Your session has expired. Please log in again.";
            case 403 -> "You do not have permission to view this addiction.";
            case 404 -> "Addiction not found. It may have been deleted.";
            case 500 -> "Server error while loading addiction details. Please try again later.";
            default -> "Unexpected error (" + status + "). Please try again.";
        };
    }
}
