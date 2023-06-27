package ru.averkiev.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.averkiev.authservice.models.RoleClass;
import ru.averkiev.authservice.models.Status;
import ru.averkiev.authservice.models.User;
import ru.averkiev.authservice.repositories.RoleRepository;
import ru.averkiev.authservice.repositories.UserRepository;
import ru.averkiev.authservice.services.UserServiceInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        RoleClass roleUser = roleRepository.findByName("USER");
        Set<RoleClass> userRoles = new HashSet<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public User update(int id, User updateUser) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
