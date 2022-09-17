package com.sikoraton.telegrambotr.service;

import com.sikoraton.tbt.dto.UserDto;

public interface UserService {
    UserDto save (UserDto userDto);
    boolean userIsPresent(Long userId);
}
