package com.sikoraton.telegrambotr.service.imp;

import com.sikoraton.telegrambotr.dto.UserDto;
import com.sikoraton.telegrambotr.mapper.UserMapper;
import com.sikoraton.telegrambotr.repository.UserRepository;
import com.sikoraton.telegrambotr.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter @Setter
@Transactional
public class UserServiceImp implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto save(UserDto userDto){
        return UserMapper.INSTANCE.entityToDto(
                repository.save(
                        UserMapper.INSTANCE.dtoToEntity(userDto)));
    }

    @Override
    public boolean userIsPresent(Long userId) {
        return repository.existsById(userId);
    }
}
