package ru.averkiev.authservice.services;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.averkiev.authservice.security.JwtProvider;
import ru.averkiev.authservice.security.JwtRequest;
import ru.averkiev.authservice.security.JwtResponse;
import ru.averkiev.authservice.security.MyUserDetails;

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


    // getAccessToken
    // refresh
    // getAuthInfo

}
