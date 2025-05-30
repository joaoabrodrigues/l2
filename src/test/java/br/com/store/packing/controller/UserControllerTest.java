package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.LoginDTO;
import br.com.store.packing.controller.dto.RecoveryTokenDTO;
import br.com.store.packing.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser() {
        LoginDTO loginDTO = new LoginDTO("testUser", "testPassword");

        RecoveryTokenDTO expectedToken = new RecoveryTokenDTO("sampleToken123");

        when(userService.authenticateUser(any(LoginDTO.class))).thenReturn(expectedToken);

        ResponseEntity<RecoveryTokenDTO> response = userController.authenticateUser(loginDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedToken, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(expectedToken.token(), response.getBody().token());
    }
}