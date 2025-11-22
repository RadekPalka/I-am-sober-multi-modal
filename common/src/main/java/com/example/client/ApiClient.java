package com.example.client;

import com.example.dto.AddictionDetailsDto;
import com.example.dto.AddictionDto;
import com.example.dto.TokenDto;
import com.example.dto.UserDto;
import com.example.exception.ApiResponseException;
import com.example.global.Global;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApiClient  {
    private final HttpClient http;
    private final ObjectMapper objectMapper;

    public ApiClient(HttpClient http, ObjectMapper json) {
        this.http = http;
        this.objectMapper = json;
    }

    private <T> T parseJson(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }


    public void register(String login, String password) throws ApiResponseException, IOException, InterruptedException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", login);
        requestBody.put("password", password);

        String json = objectMapper.writeValueAsString(requestBody);

        HttpResponse<String> response = post(Global.REGISTER_URL, json);
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return;
        }
        throw new ApiResponseException(code);




    }

    public String logIn(String login, String password) throws ApiResponseException, IOException, InterruptedException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", login);
        requestBody.put("password", password);


        String json = objectMapper.writeValueAsString(requestBody);
        HttpResponse<String> response = post(Global.LOGIN_URL, json);
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            TokenDto tokenDto = parseJson(response.body(), new TypeReference<TokenDto>() {});
            return tokenDto.getSessionToken();
        }
        throw new ApiResponseException(code);

    }

    public UserDto fetchUser(String token) throws IOException, InterruptedException, ApiResponseException {


        String url = Global.USER_DETAILS;
        HttpResponse<String> response = get(
                    url,
                    Map.of(
                            "Authorization", token.trim(),
                            "Accept", "application/json"
                    )
            );
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return parseJson(response.body(), new TypeReference<UserDto>() {
            });
        }
        throw new ApiResponseException(code);




    }



    public ArrayList<AddictionDto> getPaginatedAddictions(String token, int pageNumber) throws IOException, InterruptedException, ApiResponseException {

        String url = Global.PAGINATED_ADDICTIONS_URL + "?page=" + pageNumber;

        HttpResponse<String> response = get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return parseJson(response.body(), new TypeReference<ArrayList<AddictionDto>>(){});
        }
        throw new ApiResponseException(code);

    }


    public AddictionDetailsDto getAddictionDetails(String token, long id) throws IOException, InterruptedException, ApiResponseException {

        String url = Global.PAGINATED_ADDICTIONS_URL + "/" + id;

        HttpResponse<String> response = get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return parseJson(response.body(), new TypeReference<AddictionDetailsDto>(){});
        }
        throw new ApiResponseException(code);
    }

    public void createAddiction(String token, String addictionName, float addictionDailyCost, String detoxStartDate) throws IOException, InterruptedException, ApiResponseException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", addictionName);
        requestBody.put("costPerDay", addictionDailyCost);
        requestBody.put("detoxStartDate", detoxStartDate);

        String json = objectMapper.writeValueAsString(requestBody);
        HttpResponse<String> response = post(
                Global.PAGINATED_ADDICTIONS_URL,
                Map.of(
                        "Authorization", token.trim(),
                        "Accept", "application/json"
                ),
                json
        );
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return;
        }
        throw new ApiResponseException(code);
    }

    public void logout(String token) throws IOException, InterruptedException, ApiResponseException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sessionToken", token);

        String json = objectMapper.writeValueAsString(requestBody);
        HttpResponse<String> response = post(Global.LOGOUT_URL, json);
        int code = response.statusCode();
        if (code >= 200 && code < 300){
            return;
        }
        throw new ApiResponseException(code);

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



    private HttpResponse<String> post(String url, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return http.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> post(String url, Map<String, String> headers, String jsonBody)
            throws IOException, InterruptedException {

        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        if (headers != null) {
            headers.forEach((k, v) -> {
                if (v != null) b.header(k, v);
            });
        }

        HttpRequest request = b.build();
        return http.send(request, HttpResponse.BodyHandlers.ofString());
    }





}