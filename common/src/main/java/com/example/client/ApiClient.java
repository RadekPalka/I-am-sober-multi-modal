package com.example.client;

import com.example.dto.AddictionDto;
import com.example.auth.Session;
import com.example.dto.UserDto;
import com.example.global.Global;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApiClient  {
    private HttpClient http;
    private Session session;
    private ObjectMapper json;

    public ApiClient(Session session, HttpClient http, ObjectMapper json) {
        this.session = session;
        this.http = http;
        this.json = json;
    }

    public void register(String login, String password){

        String json = """
        {
          "username": "%s",
          "password": "%s"
        }
        """.formatted(login, password);

        try {
            HttpResponse<String> response = post(Global.REGISTER_URL, json);
            System.out.println("Your account have created successfully");
        } catch (Exception e) {
            System.out.print("Something goes wrong. Please try again");
            e.printStackTrace();

        }
    }

    public void logIn(String login, String password){
        String json = """
        {
          "username": "%s",
          "password": "%s"
        }
        """.formatted(login, password);
        try {
            HttpResponse<String> response = post(Global.LOGIN_URL, json);
            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                String token = obj.getString("sessionToken");
                session.setToken(token);
                session.setLogin(login);
                System.out.println("Logged in successfully");
            } else if (response.statusCode() == 401) {
                System.out.println("Invalid credentials");
            } else {
                System.out.println("Login failed");

            }
        } catch (Exception e) {
            System.out.println("API call failed");
            e.printStackTrace();

        }
    }

    public Optional<UserDto> fetchUser(String token) {
        if (token == null || token.isBlank()) return Optional.empty();

        try {
            String url = Global.USER_DETAILS;

            HttpResponse<String> res = get(
                    url,
                    Map.of(
                            "Authorization", token.trim(),
                            "Accept", "application/json"
                    )
            );

            int code = res.statusCode();
            if (code == 200) {
                UserDto user = json.readValue(res.body(), UserDto.class);
                return Optional.of(user);
            } else if (code == 401 || code == 403) {
                return Optional.empty();
            } else {
                System.err.println("fetchUser: HTTP " + code);
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("fetchUser failed: " + e.getMessage());
            return Optional.empty();
        }
    }



    public List<AddictionDto> getPaginatedAddictions(String token, int pageNumber) {
        try {
            String url = Global.PAGINATED_ADDICTIONS_URL + "?page=" + pageNumber;

            HttpResponse<String> res = get(url, Map.of(
                    "Authorization", token.trim(),
                    "Accept", "application/json"
            ));
            return new ArrayList<>(json.readValue(res.body(), new TypeReference<List<AddictionDto>>() {}));

        } catch (Exception e) {
            System.out.println("API call failed");
            e.printStackTrace();
            return Collections.emptyList();
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