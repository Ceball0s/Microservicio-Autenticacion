package com.Auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AuthApplicationTests {

    @Autowired
    private DataSource dataSource;

    // @Test
    // void testDatabaseConnection() throws Exception {
    //     try (Connection conn = dataSource.getConnection();
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery("SELECT 1")) {

    //         if (rs.next()) {
    //             System.out.println("✅ Conexión a la base de datos exitosa");
    //         } else {
    //             throw new RuntimeException("❌ No se pudo ejecutar SELECT 1");
    //         }

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }

}
