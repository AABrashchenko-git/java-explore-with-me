package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
