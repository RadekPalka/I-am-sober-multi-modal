package org.example.screen;

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
  void shouldSomething(){
    Scanner scanner = new Scanner("x\nr\n");
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));

    HomeScreen.resetInstance();
    HomeScreen screen = HomeScreen.getInstance(scanner);
    screen.init();

    
    String printed = output.toString();

    assertTrue(printed.contains("Choose correct option"));
  }
}