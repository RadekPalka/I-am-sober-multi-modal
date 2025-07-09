package org.example.screen;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class HomeScreenTest{
  @BeforeEach
  void resetSingleton(){
    HomeScreen.resetInstance();
  }

  @Test
  void shouldReturnSameInstance(){
    HomeScreen screen1= HomeScreen.getInstance();
    HomeScreen screen2= HomeScreen.getInstance();

    assertSame(screen1, screen2);
  }

  @Test
    void shouldPromptAgainWhenInvalidOptionAndCallRegisterScreen() {
        // Simulate user entering "x" (invalid), then "r" (valid)
        Scanner scanner = new Scanner("x\nr\n");

        // Capture printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Mock RegisterScreen
        Screen mockRegisterScreen = mock(Screen.class);

        // Set up HomeScreen
        HomeScreen screen = HomeScreen.getInstance(scanner);
        screen.setRegisterScreen(mockRegisterScreen);

        // Run the program logic
        screen.init();

        // Verify output contains error message
        String printed = output.toString();
        assertTrue(printed.contains("Choose correct option"));

        // Verify registerScreen.init() was called
        verify(mockRegisterScreen).init();
    }
}