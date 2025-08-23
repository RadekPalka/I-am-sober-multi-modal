package com.example.client;

import com.example.global.Global;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient  {
    private final HttpClient httpClient;

    public ApiClient(){
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }


    private String get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    private String post(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void register(String login, String password){
        String json = String.format("""
    {
      "username": "%s",
      "password": "%s"
    }
    """, login, password);
        try {
            String response = post(Global.REGISTER_URL, json);
            System.out.println("Response from API: " + response);
        } catch (Exception e) {
            System.out.println("API call failed: " + e.getMessage());
        }
    }

    public void logIn(String login, String password){
        String json = String.format("""
    {
      "username": "%s",
      "password": "%s"
    }
    """, login, password);
        try {
            String response = post(Global.LOGIN_URL, json);
            System.out.println("Response from API: " + response);
        } catch (Exception e) {
            System.out.println("API call failed: " + e.getMessage());
        }
    }
}