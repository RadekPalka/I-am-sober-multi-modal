package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.dto.AddictionDetailsDto;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import org.example.context.UiContext;
import java.io.IOException;
import java.util.Scanner;

public class AddictionDetailsScreen implements Screen{
    private AddictionDetailsDto addictionDetailsDto;
    private UiContext uiContext;
    private ApiClient apiClient;
    private Session session;
    private Scanner scanner;

    public AddictionDetailsScreen(UiContext uiContext, ApiClient apiClient, Session session, Scanner scanner){
        this.uiContext = uiContext;
        this.apiClient = apiClient;
        this.session = session;
        this.scanner = scanner;
    }
    @Override
    public Route init() {
        loadAddictionDetails();
        return Route.DASHBOARD;
    }

    private void loadAddictionDetails(){
        try{
            addictionDetailsDto = apiClient.getAddictionDetails(session.getToken(), uiContext.getSelectedAddictionId());
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
