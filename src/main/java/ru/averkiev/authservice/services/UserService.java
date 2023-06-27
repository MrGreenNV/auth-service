package ru.averkiev.authservice.services;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.averkiev.authservice.models.Role;
import ru.averkiev.authservice.models.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Упрощенный сервис без создания пользователей и шифрования паролей.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final List<User> users;

    public UserService() {
        this.users = List.of(
//                new User("ivan", "12345", "ivan", "ivanovich", Collections.singleton(Role.USER)),
//                new User("nikolay", "123456", "nikolay", "nikolayevich", Collections.singleton(Role.ADMIN))
        );
    }

    public Optional<User> getByLogin(@NotNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
