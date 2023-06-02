package ru.averkiev.authservice.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER");

    Role(String vale) {
        this.vale = vale;
    }

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }
}
