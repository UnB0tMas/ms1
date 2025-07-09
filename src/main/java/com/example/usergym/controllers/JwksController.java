// src/main/java/com/example/usergym/controllers/JwksController.java
package com.example.usergym.controllers;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyPair;
import java.util.Map;

@RestController
public class JwksController {

    private final KeyPair keyPair;

    public JwksController(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // Puedes poner un "kid" fijo, o calcularlo con un hash si quieres
        RSAKey key = new RSAKey.Builder(publicKey)
                .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
                .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
                .keyID("usergym-key") // Puedes poner un nombre que quieras
                .build();
        return new JWKSet(key).toJSONObject();
    }
}
