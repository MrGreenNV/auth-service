package ru.averkiev.authservice.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Объект данного класса отправляется пользователем, для получеия JWT токена.
 */

@Setter
@Getter
public class JwtRequest {
    private String login;
    private String password;
}
