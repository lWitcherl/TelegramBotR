package com.sikoraton.telegrambotr.repository;

import com.sikoraton.tbt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
}
