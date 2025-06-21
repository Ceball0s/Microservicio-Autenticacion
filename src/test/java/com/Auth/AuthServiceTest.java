package com.Auth;

import com.Auth.Exceptions.EmailAlreadyUsedException;
import com.Auth.Request.AuthResponse;
import com.Auth.Request.LoginRequest;
import com.Auth.Request.RegisterRequest;
import com.Auth.User.Role;
import com.Auth.User.User;
import com.Auth.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        registerRequest = RegisterRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        loginRequest = new LoginRequest("test@example.com", "password123");
    }

    @Test
    void whenRegisterWithNewEmail_thenSucceeds() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("test-token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("test-token", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenRegisterWithExistingEmail_thenThrowsException() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

        EmailAlreadyUsedException exception = assertThrows(EmailAlreadyUsedException.class,
                () -> authService.register(registerRequest));
        
        assertEquals("El correo ya está en uso", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenLoginWithValidCredentials_thenSucceeds() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("test-token");

        AuthResponse response = authService.login(loginRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertNotNull(response);
        assertEquals("test-token", response.getToken());
    }

    @Test
    void whenLoginWithNonExistentUser_thenThrowsException() {
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(loginRequest));
        
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(jwtService, never()).generateToken(any(User.class));
    }
} 