package ru.averkiev.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.averkiev.authservice.models.RoleClass;

public interface RoleRepository extends JpaRepository<RoleClass, Integer> {
    RoleClass findByName(String name);
}
