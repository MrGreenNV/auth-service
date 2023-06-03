package ru.averkiev.authservice.services;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.averkiev.authservice.models.User;
import ru.averkiev.authservice.security.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public AuthService(UserDetailsService userDetailsService, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse login(@NotNull JwtRequest authRequest) throws AuthException {
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(authRequest.getLogin());

        String password = userDetails.getPassword();

        if (password.equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(userDetails.getUser());
            final String refreshToken = jwtProvider.generateRefreshToken(userDetails.getUser());
            refreshStorage.put(userDetails.getUser().getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль!");
        }
    }

    public JwtResponse getAccessToken(@NotNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userDetailsService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }

        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NotNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userDetailsService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }

        throw new AuthException("Неверный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
