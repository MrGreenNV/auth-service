package ru.averkiev.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.authservice.models.User;
import ru.averkiev.authservice.services.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserServiceImpl userService;

    @PostMapping()
    public ResponseEntity<User> register(@RequestBody User user) {
        System.out.println("tut");
        return ResponseEntity.ok(userService.register(user));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.ok(userService.getAll());
    }
}
