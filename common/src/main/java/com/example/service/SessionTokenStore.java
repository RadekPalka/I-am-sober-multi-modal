package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SessionTokenStore {
    public static String os = System.getProperty("os.name").toLowerCase();
    public static String appName = "IAmSober";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Path resolveTokenPath(){
        if (os.contains("win")){
            String appData = System.getenv("APPDATA");
            return Paths.get(appData, appName, "session.json");
        }
        else if (os.contains("mac")) {
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support", appName, "session.json");
        }
        else {
            String xdg = System.getenv("XDG_CONFIG_HOME");
            if (xdg != null && !xdg.isBlank()) {
                return Paths.get(xdg, appName, "session.json");
            } else {
                return Paths.get(System.getProperty("user.home"), ".config", appName, "session.json");
            }
        }
    }

    public static void saveToken(String token){
        try{
            Path file = resolveTokenPath();
            Files.createDirectories(file.getParent());
            System.out.println("Saving token to: " + file.toAbsolutePath());
            mapper.writeValue(file.toFile(), Map.of("sessionToken", token));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String loadToken() throws IOException {
        Path file = resolveTokenPath();
        return Files.readString(file);
    }
}
