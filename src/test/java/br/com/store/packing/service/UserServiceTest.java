package br.com.store.packing.service;

import br.com.store.packing.controller.dto.LoginDTO;
import br.com.store.packing.controller.dto.RecoveryTokenDTO;
import br.com.store.packing.domain.entity.User;
import br.com.store.packing.domain.repository.UserRepository;
import br.com.store.packing.config.SecurityConfiguration;
import br.com.store.packing.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityConfiguration securityConfiguration;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_ValidCredentials() {
        LoginDTO loginDTO = new LoginDTO("testUser", "testPassword");

        User user = new User();
        user.setEmail("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(securityConfiguration.passwordEncoder()).thenReturn(encoder);
        when(encoder.matches("testPassword", "encodedPassword")).thenReturn(true);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "testPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenService.generateToken(userDetails)).thenReturn("sampleToken123");

        RecoveryTokenDTO result = userService.authenticateUser(loginDTO);

        assertNotNull(result);
        assertEquals("sampleToken123", result.token());
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        LoginDTO loginDTO = new LoginDTO("testUser", "testPassword");
        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userService.authenticateUser(loginDTO));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        LoginDTO loginDTO = new LoginDTO("testUser", "wrongPassword");
        User user = new User();
        user.setEmail("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));
        when(securityConfiguration.passwordEncoder()).thenReturn(encoder);
        when(encoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser(loginDTO));

        assertEquals("Invalid password", exception.getMessage());
    }
}