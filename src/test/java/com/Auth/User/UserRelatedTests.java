package com.Auth.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRelatedTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();
    }
    
    @Test
    void testUserGettersAndSetters() {
        User newUser = new User();
        newUser.setId(2);
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("newpass");
        newUser.setRole(Role.ADMIN);

        assertEquals(2, newUser.getId());
        assertEquals("newuser", newUser.getUsername());
        assertEquals("new@example.com", newUser.getEmail());
        assertEquals("newpass", newUser.getPassword());
        assertEquals(Role.ADMIN, newUser.getRole());
    }

    @Test
    void testUserConstructors() {
        User allArgsUser = new User(3, "allargs", "all@args.com", "pass", Role.USER);
        assertEquals(3, allArgsUser.getId());
        assertEquals("allargs", allArgsUser.getUsername());

        User noArgsUser = new User();
        assertNull(noArgsUser.getId());
    }

    @Test
    void testUserDetailsMethods() {
        assertEquals("password", user.getPassword());
        assertEquals("testuser", user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertNull(user.getAuthorities());
    }
    
    @Test
    void testUserEqualsAndHashCodeAndToString() {
        User sameUser = User.builder()
                .id(1)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        User differentUser = User.builder().id(2).build();

        assertEquals(user, sameUser);
        assertNotEquals(user, differentUser);
        assertEquals(user.hashCode(), sameUser.hashCode());
        assertNotEquals(user.hashCode(), differentUser.hashCode());
        assertNotNull(user.toString());
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");
        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findByEmail("wrong@email.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("wrong@email.com");
        });
    }

    @Test
    void testRoleEnum() {
        assertEquals(2, Role.values().length);
        assertEquals(Role.USER, Role.valueOf("USER"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
    }
} 