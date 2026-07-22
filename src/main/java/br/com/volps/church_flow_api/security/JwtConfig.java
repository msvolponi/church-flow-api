package br.com.volps.church_flow_api.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtDecoder jwtDecoder() {
        var key = new SecretKeySpec(
                secret.getBytes(),
                "HmacSHA384");

        return NimbusJwtDecoder
                .withSecretKey(key)
                .build();
    }
}
