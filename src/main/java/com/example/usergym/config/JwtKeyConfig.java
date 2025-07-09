package com.example.usergym.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.*;
import java.util.stream.Collectors;

@Configuration
public class JwtKeyConfig {

    @Value("${jwt.private-key}") private String privateKeyPath;
    @Value("${jwt.public-key}")  private String publicKeyPath;

    @Bean
    public KeyPair jwtKeyPair() throws Exception {
        // Leer PEM y limpiar sólo la parte Base64
        String privPem = loadPemAsBase64(privateKeyPath);
        String pubPem  = loadPemAsBase64(publicKeyPath);

        byte[] privBytes = java.util.Base64.getDecoder().decode(privPem);
        byte[] pubBytes  = java.util.Base64.getDecoder().decode(pubPem);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privBytes);
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubBytes);

        return new KeyPair(
                kf.generatePublic(pubSpec),
                kf.generatePrivate(privSpec)
        );
    }

    private String loadPemAsBase64(String resourcePath) throws Exception {
        try (InputStream is = getClass().getResourceAsStream(
                resourcePath.replace("classpath:", "/"))) {
            if (is == null) {
                throw new IllegalArgumentException("No se encontró recurso " + resourcePath);
            }
            // Leer todo, filtrar líneas que empiecen con '---' y unir sin espacios
            BufferedReader rdr = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return rdr.lines()
                    .filter(line -> !line.startsWith("-----"))
                    .collect(Collectors.joining());
        }
    }
}
