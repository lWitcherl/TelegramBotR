package com.sikoraton.telegrambotr.service.imp;

import com.sikoraton.tbt.dto.UserDto;
import com.sikoraton.tbt.mapper.UserMapper;
import com.sikoraton.tbt.repository.UserRepository;
import com.sikoraton.tbt.service.UserService;
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
