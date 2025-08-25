package com.example.client;

import com.example.auth.Session;
import com.example.global.Global;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ApiClient  {
    private final HttpClient http;
    private Session session;

    public ApiClient(Session session){
        this.session = session;
        this.http = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
    }

    public void register(String login, String password){
        String json = String.format("""
    {
      "username": "%s",
      "password": "%s"
    }
    """, login, password);
        try {
            HttpResponse<String> response = post(Global.REGISTER_URL, json);
            System.out.println("Response from API: " + response.body());
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
                System.out.println("Login failed. Status: " + response.statusCode());
                System.out.println("Response: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("API call failed: " + e.getMessage());
        }
    }

    public void getPaginatedAddictions(String token, int pageNumber) {
        try {
            String url = Global.PAGINATED_ADDICTIONS_URL + "?page=" + pageNumber;

            HttpResponse<String> res = get(url, Map.of(
                    "Authorization", token.trim(),         // u Ciebie: BEZ "Bearer"
                    "Accept", "application/json"
            ));

            System.out.println("STATUS: " + res.statusCode());
            System.out.println("BODY: " + res.body());
        } catch (Exception e) {
            System.out.println("API call failed: " + e.getMessage());
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