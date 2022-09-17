package com.sikoraton.telegrambotr.mapper;

import com.sikoraton.tbt.dto.RecordDto;
import com.sikoraton.tbt.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);
    RecordDto entityToDto (Record user);
    Record dtoToEntity (RecordDto user);
}
