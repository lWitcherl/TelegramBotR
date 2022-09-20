package com.sikoraton.telegrambotr.service;

import com.sikoraton.telegrambotr.dto.GameRoomDto;

public interface GameRoomService {
    Long createGameRoom(Long user);
    GameRoomDto getGameRoom(Long id);
    boolean roomIsPresent(Long id);
    String getRandomRecordByRoomId(Long id);
}
