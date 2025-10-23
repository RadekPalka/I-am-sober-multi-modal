package com.example.client;


import com.example.dto.AddictionDetailsDto;
import com.example.auth.Session;
import com.example.global.Global;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApiClient  {
    private HttpClient http;
    private ObjectMapper objectMapper;

    public ApiClient(HttpClient http, ObjectMapper json) {
        this.http = http;
        this.objectMapper = json;
    }

    public HttpResponse<String> register(String login, String password){

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", login);
        requestBody.put("password", password);


        try {
            String json = objectMapper.writeValueAsString(requestBody);
            return post(Global.REGISTER_URL, json);






        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();

        }
    }

    public HttpResponse<String> logIn(String login, String password){

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", login);
        requestBody.put("password", password);

        try {
            String json = objectMapper.writeValueAsString(requestBody);
            return post(Global.LOGIN_URL, json);



        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();

        }

    }

    public HttpResponse<String> fetchUser(String token) {

        try {
            String url = Global.USER_DETAILS;

            return get(
                    url,
                    Map.of(
                            "Authorization", token.trim(),
                            "Accept", "application/json"
                    )
            );



        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();
        }
    }



    public HttpResponse<String> getPaginatedAddictions(String token, int pageNumber) {
        try {
            String url = Global.PAGINATED_ADDICTIONS_URL + "?page=" + pageNumber;

            return get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));


        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();
        }

    }


    public HttpResponse<String> getAddictionDetails(String token, long id){
        try{
            String url = Global.PAGINATED_ADDICTIONS_URL + "/" + id;

             return get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));
            return Optional.of(objectMapper.readValue(res.body(), AddictionDetailsDto.class));
        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();
        }
    }

    public HttpResponse<String> logout(String token){

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sessionToken", token);
        try {
            String json = objectMapper.writeValueAsString(requestBody);
            return post(Global.LOGOUT_URL, json);

        } catch (Exception e) {
            // TODO create Exception
            throw new RuntimeException();

        }

    }




    private HttpResponse<String> get(String url, Map<String, String> headers)
            throws IOException, InterruptedException {

        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        if (headers != null) {
            headers.forEach((k, v) -> { if (v != null) b.header(k, v); });
        }

        HttpRequest request = b.build();
        return http.send(request, HttpResponse.BodyHandlers.ofString());
    }



    private HttpResponse<String> post(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return http.send(request, HttpResponse.BodyHandlers.ofString());
    }




}