package br.com.store.packing.service;

import br.com.store.packing.security.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    @Value("secretKey")
    private String secretKey;

    public String generateToken(UserDetailsImpl user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String getSubjectFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getSubject();
    }
}
