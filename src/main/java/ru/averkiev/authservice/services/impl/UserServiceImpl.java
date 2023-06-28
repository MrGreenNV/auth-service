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
import ru.averkiev.authservice.services.UserService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

        Date now = new Date();

        user.setCreated(now);
        user.setUpdated(now);

        userRepository.save(user);

        log.info("IN register - user: {} successfully registered", user);

        return user;
    }

    @Override
    public User update(int id, User updateUser) {
        updateUser.setId(id);
        updateUser.setUpdated(new Date());

        userRepository.save(updateUser);
        log.info("IN update - user: {} successfully updated", updateUser);
        return updateUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN getAll - {} users found", users.size());
        return users;
    }

    @Override
    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        log.info("IN findByLogin - user: {} found by login: {}", user, login);
        return user;
    }

    @Override
    public User findById(int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN findByLogin - no user found by id: {}", id);
            return null;
        }

        log.info("IN findByLogin - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public boolean delete(int id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN delete - no user found by id: {}", id);
            return false;
        }

        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);

        return true;
    }
}
