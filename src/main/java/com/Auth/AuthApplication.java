package com.Auth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String[] args) {
        // Configura Dotenv con manejo seguro de valores nulos
        // Dotenv dotenv = Dotenv.configure()
        //         .directory("./") // Busca en el directorio raíz del proyecto
        //         .ignoreIfMissing() // No lanza excepción si no encuentra el archivo
        //         .load();

        // // Establece propiedades con valores por defecto si es necesario
        // setPropertySafe(dotenv, "DB_URL", "jdbc:postgresql://db_autenticacion:5432/ofertaya");
        // setPropertySafe(dotenv, "DB_USERNAME", "postgres");
        // setPropertySafe(dotenv, "DB_PASSWORD", "postgres");
        // setPropertySafe(dotenv, "JWT_SECRET_KEY", "a-string-secret-at-least-256-bits-long");
        // setPropertySafe(dotenv, "JWT_EXPIRATION", "86400000"); // 24 horas

        SpringApplication.run(AuthApplication.class, args);
    }

    private static void setPropertySafe(Dotenv dotenv, String key, String defaultValue) {
        String value = dotenv.get(key, defaultValue);
        if (value != null) {
            System.setProperty(key, value);
        }
    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();
    }
}