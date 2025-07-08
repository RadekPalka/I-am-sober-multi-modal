package org.example.screen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeScreenTest{

  @Test
  void shouldReturnSameInstance(){
    HomeScreen screen1= HomeScreen.getInstance();
    HomeScreen screen2= HomeScreen.getInstance();

    assertSame(screen1, screen2);
  }
}