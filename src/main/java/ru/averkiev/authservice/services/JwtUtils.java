package ru.averkiev.authservice.services;

import io.jsonwebtoken.Claims;
import ru.averkiev.authservice.models.Role;
import ru.averkiev.authservice.security.JwtAuthentication;

public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(claims.get("role", Role.class));
        jwtInfoToken.setEmail(claims.get("email", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }
}
