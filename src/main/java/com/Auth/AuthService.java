package com.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Auth.User.Role;
import com.Auth.User.User;
import com.Auth.User.UserRepository;
import com.Auth.Request.AuthResponse;
import com.Auth.Request.RegisterRequest;
import com.Auth.Request.LoginRequest;
import com.Auth.Exceptions.EmailAlreadyUsedException;
//import com.Auth.Jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final PasswordEncoder passwordEncoder;
   private final UserRepository userRepository;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;

   public AuthResponse register(RegisterRequest request) {
      if (userRepository.findByEmail(request.getEmail()).isPresent()) {
         throw new EmailAlreadyUsedException("El correo ya está en uso");
      }

      var user = User.builder()
         .username(request.getUsername())
         .email(request.getEmail())
         .password(passwordEncoder.encode(request.getPassword()))
         .role(Role.USER)
         .build();

      userRepository.save(user);

      var jwtToken = jwtService.generateToken(user);
      return AuthResponse.builder()
         .token(jwtToken)
         .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
