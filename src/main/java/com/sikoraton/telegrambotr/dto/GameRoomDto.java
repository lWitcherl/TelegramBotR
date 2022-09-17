package com.sikoraton.telegrambotr.dto;

import java.util.Set;

import lombok.Data;

@Data
public class GameRoomDto {
    private Long id;
    private UserDto creator;
    private Set<RecordDto> records;

}
