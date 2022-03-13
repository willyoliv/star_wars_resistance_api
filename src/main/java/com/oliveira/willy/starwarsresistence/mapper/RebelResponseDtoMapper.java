package com.oliveira.willy.starwarsresistence.mapper;

import com.oliveira.willy.starwarsresistence.dto.RebelResponseDto;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebelResponseDtoMapper {
    RebelResponseDto rebelToRebelResponseDto(Rebel rebel);
}
