package ru.averkiev.authservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Объект данного класса будет содежрать access и refresh токены.
 */

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}
