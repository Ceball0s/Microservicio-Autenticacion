package com.Auth.Request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DtoTests {

    @Test
    void testAuthResponse() {
        // Test builder and getter
        AuthResponse response1 = AuthResponse.builder().token("token1").build();
        assertEquals("token1", response1.getToken());

        // Test Data annotation (toString, equals, hashCode)
        AuthResponse response2 = AuthResponse.builder().token("token1").build();
        AuthResponse response3 = AuthResponse.builder().token("token2").build();

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        assertNotNull(response1.toString());
    }

    @Test
    void testLoginRequest() {
        // Test NoArgsConstructor and setters
        LoginRequest request1 = new LoginRequest();
        request1.setEmail("user1@test.com");
        request1.setPassword("pass1");
        assertEquals("user1@test.com", request1.getEmail());
        assertEquals("pass1", request1.getPassword());
        
        // Test AllArgsConstructor and getters
        LoginRequest request2 = new LoginRequest("user2@test.com", "pass2");
        assertEquals("user2@test.com", request2.getEmail());
        assertEquals("pass2", request2.getPassword());

        // Test builder
        LoginRequest request3 = LoginRequest.builder()
            .email("user3@test.com")
            .password("pass3")
            .build();
        assertEquals("user3@test.com", request3.getEmail());
        assertEquals("pass3", request3.getPassword());
        
        // Test Data annotation (toString, equals, hashCode)
        LoginRequest request4 = new LoginRequest("user2@test.com", "pass2");
        assertEquals(request2, request4);
        assertNotEquals(request1, request2);
        assertEquals(request2.hashCode(), request4.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());
        assertNotNull(request1.toString());
    }

    @Test
    void testRegisterRequest() {
        // Test NoArgsConstructor and setters
        RegisterRequest request1 = new RegisterRequest();
        request1.setUsername("user1");
        request1.setPassword("pass1");
        request1.setEmail("email1@test.com");
        request1.setFirstname("first1");
        request1.setLastname("last1");
        request1.setCountry("co1");
        request1.setDate("date1");
        request1.setGender("m1");
        
        assertEquals("user1", request1.getUsername());
        assertEquals("pass1", request1.getPassword());
        assertEquals("email1@test.com", request1.getEmail());
        assertEquals("first1", request1.getFirstname());
        assertEquals("last1", request1.getLastname());
        assertEquals("co1", request1.getCountry());
        assertEquals("date1", request1.getDate());
        assertEquals("m1", request1.getGender());

        // Test AllArgsConstructor and getters
        RegisterRequest request2 = new RegisterRequest("user2", "pass2", "email2@test.com", "first2", "last2", "co2", "date2", "m2");
        assertEquals("user2", request2.getUsername());
        assertEquals("pass2", request2.getPassword());
        assertEquals("email2@test.com", request2.getEmail());
        assertEquals("first2", request2.getFirstname());
        assertEquals("last2", request2.getLastname());
        assertEquals("co2", request2.getCountry());
        assertEquals("date2", request2.getDate());
        assertEquals("m2", request2.getGender());
        
        // Test builder
        RegisterRequest request3 = RegisterRequest.builder()
            .username("user3").password("pass3").email("email3@test.com")
            .firstname("first3").lastname("last3").country("co3")
            .date("date3").gender("m3").build();
        assertEquals("user3", request3.getUsername());
        assertEquals("email3@test.com", request3.getEmail());

        // Test Data annotation (toString, equals, hashCode)
        RegisterRequest request4 = new RegisterRequest("user2", "pass2", "email2@test.com", "first2", "last2", "co2", "date2", "m2");
        assertEquals(request2, request4);
        assertNotEquals(request1, request2);
        assertEquals(request2.hashCode(), request4.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());
        assertNotNull(request1.toString());
    }
} 