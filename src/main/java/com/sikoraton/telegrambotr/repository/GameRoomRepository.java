package com.sikoraton.telegrambotr.repository;

import com.sikoraton.telegrambotr.entity.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
    boolean existsById(Long id);
}
