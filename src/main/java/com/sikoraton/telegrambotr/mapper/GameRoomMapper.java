package com.sikoraton.telegrambotr.mapper;

import com.sikoraton.telegrambotr.dto.GameRoomDto;
import com.sikoraton.telegrambotr.entity.GameRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameRoomMapper {
    GameRoomMapper INSTANCE = Mappers.getMapper(GameRoomMapper.class);
    GameRoomDto entityToDto (GameRoom user);
    GameRoom dtoToEntity (GameRoomDto user);
}
