package com.sikoraton.telegrambotr.repository;

import com.sikoraton.telegrambotr.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(nativeQuery=true, value="SELECT *  FROM record where game_room_id = ?1 ORDER BY random() LIMIT 1")
    Record findRandomRecordByGameRoomId(Long id);
}
