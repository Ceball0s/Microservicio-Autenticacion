package com.Auth.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    
    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna los roles o permisos del usuario
        return null; // o tu implementación
    }

    @Override
    public String getPassword() {
        // Devuelve la contraseña del usuario
        return this.password;
    }

    @Override
    public String getUsername() {
        // Devuelve el nombre de usuario
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // o según lógica
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // o según lógica
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // o según lógica
    }

    @Override
    public boolean isEnabled() {
        return true; // o según lógica
    }
    
}
