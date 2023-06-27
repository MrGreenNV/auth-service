package ru.averkiev.authservice.services;

import ru.averkiev.authservice.models.User;

import java.util.List;

public interface UserServiceInterface {

    User register(User user);

    User update(int id, User updateUser);

    List<User> getAll();

    User findByLogin(String login);

    User findById(int id);

    boolean delete(int id);
}
