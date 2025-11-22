package org.example.screen;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class AddAddictionScreen implements Screen{
    private final Scanner scanner;
    private final ApiClient apiClient;
    private final Session session;

    public AddAddictionScreen(Scanner scanner, ApiClient apiClient, Session session){
        this.scanner = scanner;
        this.apiClient = apiClient;
        this.session = session;
    }

    @Override
    public RoutingData init() {
        Route route = handleCreateAddiction();
        return new RoutingData(route);
    }

    private Route handleCreateAddiction(){
        Optional<String> addictionNameOpt= getAddictionName();
        Optional<Float> addictionDailyCostOpt = getAddictionDailyCost();
        Optional<String> detoxStartDateOpt = getDetoxStartDate();

        if(addictionNameOpt.isEmpty() || addictionDailyCostOpt.isEmpty() || detoxStartDateOpt.isEmpty()){
            return Route.DASHBOARD;
        }
        try{
            apiClient.createAddiction(session.getToken(), addictionNameOpt.get(), addictionDailyCostOpt.get(), detoxStartDateOpt.get());
            System.out.println("Addiction created successfully");
            session.markAddictionsForReload();
            return Route.DASHBOARD;
        }
        catch (ApiResponseException e) {
            System.out.println(errorMessageForAddAddiction(e));
            return Route.ADD_ADDICTION;
        }
        catch (IOException | InterruptedException e){
            System.out.println("Network error. Please check your connection.");
            return Route.ADD_ADDICTION;
        }
    }

    private Optional<String> getAddictionName(){
        System.out.println("What's the addiction name?");
        String addictionName = scanner.nextLine();
        if (addictionName.isBlank()){
            return Optional.empty();
        }
        return Optional.of(addictionName);
    }

    private Optional<Float> getAddictionDailyCost(){
        System.out.println("What's the addiction daily cost?");
        String input = scanner.nextLine();
        try{
            float addictionDailyCost = Float.parseFloat(input);
            if (addictionDailyCost < 0){
                System.out.println("Addiction cost shouldn't be negative.");
                return Optional.empty();
            }
            return Optional.of(addictionDailyCost);
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format.");
            return Optional.empty();
        }
    }

    private Optional<String> getDetoxStartDate(){
        System.out.println("What's the detox start date? (yyyy-MM-dd)");
        String input = scanner.nextLine();
        try{
            LocalDate date = LocalDate.parse(input);
            if (date.isAfter(LocalDate.now())){
                System.out.println("Date shouldn't be in the future.");
                return Optional.empty();
            }
            return Optional.of(input);
        } catch (DateTimeParseException e) {
            System.out.println("Wrong data format");
            return Optional.empty();
        }
    }

    private String errorMessageForAddAddiction(ApiResponseException e) {
        int status = e.getStatusCode();

        return switch (status) {
            case 400 -> "Invalid addiction data. Please check the form and try again.";
            case 401 -> "Your session has expired. Please log in again.";
            case 403 -> "You do not have permission to create addictions.";
            case 404 -> "Server endpoint not found.";
            case 409 -> "An addiction with this name already exists.";
            case 500 -> "Server error while creating the addiction. Please try again later.";
            default -> "Unexpected error (" + status + "). Please try again.";
        };
    }

}
