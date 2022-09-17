package com.sikoraton.telegrambotr.mapper;

import com.sikoraton.tbt.dto.UserDto;
import com.sikoraton.tbt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto entityToDto (User user);
    UserDto tUserToUserDto (org.telegram.telegrambots.meta.api.objects.User user);
    User dtoToEntity (UserDto user);
}
