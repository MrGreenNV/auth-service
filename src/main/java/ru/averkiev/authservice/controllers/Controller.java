package ru.averkiev.authservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.averkiev.authservice.security.JwtAuthentication;
import ru.averkiev.authservice.services.AuthService;

@RestController
@RequestMapping("api")
public class Controller {

    private final AuthService authService;

    public Controller(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }


}
