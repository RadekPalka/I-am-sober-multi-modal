package org.example.core;

import com.example.auth.Session;
import com.example.client.ApiClient;
import com.example.dto.UserDto;
import com.example.exception.ApiResponseException;
import com.example.routing.Route;
import com.example.service.SessionTokenStore;
import org.example.screen.RoutingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.util.Optional;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreenManagerDecideInitialRouteTest {

    Session session;
    ApiClient apiClient;
    RoutingData routingData;

    @BeforeEach
    void setUp() {
        session = mock(Session.class);
        apiClient = mock(ApiClient.class);
        routingData = new RoutingData();
    }


    @Test
    void whenTokenOnDiskAndBackendOk_shouldSetSessionAndReturnDashboard() throws Exception {


        try (MockedStatic<SessionTokenStore> tokenStoreMock = mockStatic(SessionTokenStore.class)) {

            tokenStoreMock.when(SessionTokenStore::loadToken)
                    .thenReturn(Optional.of("TOKEN_123"));


            UserDto userDto = new UserDto();
            userDto.setUsername("radek");
            when(apiClient.fetchUser("TOKEN_123")).thenReturn(userDto);

            ScreenManager screenManager = new ScreenManager(session, apiClient, routingData);

            // when
            Route initialRoute = screenManager.decideInitialRoute();

            // then
            assertEquals(Route.DASHBOARD, initialRoute);


            verify(session).setLoginAndToken("radek", "TOKEN_123");


            tokenStoreMock.verify(SessionTokenStore::clearToken, never());
        }
    }

    @Test
    void whenTokenNotOnDisk_shouldReturnHomeAndNotTouchSessionOrApiClient() throws Exception {

        try (MockedStatic<SessionTokenStore> tokenStoreMock = mockStatic(SessionTokenStore.class)) {
            tokenStoreMock.when(SessionTokenStore::loadToken)
                    .thenReturn(Optional.empty());

            ScreenManager screenManager = new ScreenManager(session, apiClient, routingData);

            // when
            Route initialRoute = screenManager.decideInitialRoute();

            // then
            assertEquals(Route.HOME, initialRoute);

            verifyNoInteractions(apiClient);

            verify(session, never()).setLoginAndToken(any(), any());

            tokenStoreMock.verify(SessionTokenStore::clearToken, never());
        }
    }

    @Test
    void whenTokenOnDiskAndBackendReturns401_shouldClearTokenAndReturnHome() throws Exception {

        try (MockedStatic<SessionTokenStore> tokenStoreMock = mockStatic(SessionTokenStore.class)) {

            tokenStoreMock.when(SessionTokenStore::loadToken)
                    .thenReturn(Optional.of("TOKEN_123"));

            when(apiClient.fetchUser("TOKEN_123"))
                    .thenThrow(new ApiResponseException(401, "Unauthorized"));

            ScreenManager screenManager = new ScreenManager(session, apiClient, routingData);


            // when + then

            String output = tapSystemOut(() -> {
                Route initialRoute = screenManager.decideInitialRoute();
                assertEquals(Route.HOME, initialRoute);
            });


            verify(apiClient).fetchUser("TOKEN_123");

            verify(session, never()).setLoginAndToken(any(), any());

            tokenStoreMock.verify(SessionTokenStore::clearToken);

            assertTrue(output.contains("Saved session has expired or is invalid. Please log in again."));
        }
    }



}
