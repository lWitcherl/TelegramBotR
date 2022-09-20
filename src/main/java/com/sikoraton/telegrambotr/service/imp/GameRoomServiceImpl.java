package com.sikoraton.telegrambotr.service.imp;

import com.sikoraton.telegrambotr.dto.GameRoomDto;
import com.sikoraton.telegrambotr.entity.GameRoom;
import com.sikoraton.telegrambotr.entity.Record;
import com.sikoraton.telegrambotr.repository.GameRoomRepository;
import com.sikoraton.telegrambotr.repository.RecordRepository;
import com.sikoraton.telegrambotr.repository.UserRepository;
import com.sikoraton.telegrambotr.service.GameRoomService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter @Setter
@Transactional
public class GameRoomServiceImpl implements GameRoomService {
    private GameRoomRepository repository;
    private RecordRepository recordRepository;
    private UserRepository userRepository;

    @Autowired
    public GameRoomServiceImpl(GameRoomRepository repository,
                               UserRepository userRepository,
                               RecordRepository recordRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
    }

    @Override
    public Long createGameRoom(Long userId) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setUser(userRepository.getById(userId));
        return repository.save(gameRoom).getId();
    }

    @Override
    public GameRoomDto getGameRoom(Long id) {
        return null;
    }

    @Override
    public boolean roomIsPresent(Long id) {
        return repository.existsById(id);
    }

    @Override
    public String getRandomRecordByRoomId(Long id) {
        Record record = recordRepository.findRandomRecordByGameRoomId(id);
        if(record == null) return null;
        return record.getTask();
    }
}
