package ru.averkiev.authservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.averkiev.authservice.models.RoleClass;
import ru.averkiev.authservice.models.Status;
import ru.averkiev.authservice.models.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {

    }

    public static JwtUser created(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(new HashSet<>(user.getRoles()))
        );
    }

    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<RoleClass> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}
