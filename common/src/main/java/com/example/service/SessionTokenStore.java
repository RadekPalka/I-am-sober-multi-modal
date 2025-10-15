package com.example.service;

import com.example.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

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

    public static Optional<String> loadToken() {

        try{
            Path file = resolveTokenPath();
            if (!Files.exists(file)){
                return Optional.empty();
            }
            String json= Files.readString(file);
            if (json.isEmpty()){
                return Optional.empty();
            }
            TokenDto tokenDto = mapper.readValue(json, TokenDto.class);
            String token = tokenDto.getSessionToken();
            if (token == null || token.isBlank()) {
                return Optional.empty();
            }
            return Optional.of(token);

        } catch (IOException ignored) {
            return Optional.empty();
        }
    }

    public static void clearToken() {
        try {
            Path file = resolveTokenPath();
            if (Files.exists(file)) {
                Files.delete(file);
                System.out.println("Session token file deleted: " + file.toAbsolutePath());
            } else {
                System.out.println("No session token file found to delete.");
            }
        } catch (IOException e) {
            System.err.println("Failed to delete session token file: " + e.getMessage());
        }
    }

}
