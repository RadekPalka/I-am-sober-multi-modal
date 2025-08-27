package com.example.client;

import java.net.http.HttpClient;
import java.time.Duration;

public final class HttpClients {
    private HttpClients(){}
    public static HttpClient defaultClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
    }
}
