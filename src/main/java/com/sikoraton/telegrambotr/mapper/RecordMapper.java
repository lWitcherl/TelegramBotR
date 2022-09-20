package com.sikoraton.telegrambotr.mapper;

import com.sikoraton.telegrambotr.dto.RecordDto;
import com.sikoraton.telegrambotr.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);
    RecordDto entityToDto (Record user);
    Record dtoToEntity (RecordDto user);
}
