package ru.averkiev.authservice.services;

import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import ru.averkiev.authservice.models.Role;
import ru.averkiev.authservice.security.JwtAuthentication;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
//        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstname(claims.get("firstname", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}