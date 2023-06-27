package ru.averkiev.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.averkiev.authservice.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
