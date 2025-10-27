package com.example.client;

import com.example.dto.TokenDto;
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

    public <T> T parseJson(String json, TypeReference<T> typeRef) {
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
        if (code == 201){
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
        if (code == 200){
            TokenDto tokenDto = parseJson(response.body(), new TypeReference<TokenDto>() {});
            return tokenDto.getSessionToken();
        }
        throw new ApiResponseException(code);

    }

    public HttpResponse<String> fetchUser(String token) throws IOException, InterruptedException {


        String url = Global.USER_DETAILS;
        return get(
                    url,
                    Map.of(
                            "Authorization", token.trim(),
                            "Accept", "application/json"
                    )
            );




    }



    public HttpResponse<String> getPaginatedAddictions(String token, int pageNumber) throws IOException, InterruptedException {

        String url = Global.PAGINATED_ADDICTIONS_URL + "?page=" + pageNumber;

        return get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));




    }


    public HttpResponse<String> getAddictionDetails(String token, long id) throws IOException, InterruptedException {

        String url = Global.PAGINATED_ADDICTIONS_URL + "/" + id;

        return get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));

    }

    public HttpResponse<String> logout(String token) throws Exception {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sessionToken", token);

        String json = objectMapper.writeValueAsString(requestBody);
        return post(Global.LOGOUT_URL, json);


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




}