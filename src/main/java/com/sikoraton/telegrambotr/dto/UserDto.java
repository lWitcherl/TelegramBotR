package com.sikoraton.telegrambotr.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String userName;
    private Long chatId;

}
