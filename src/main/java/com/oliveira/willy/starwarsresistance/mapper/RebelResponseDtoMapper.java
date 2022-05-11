package com.oliveira.willy.starwarsresistance.mapper;

import com.oliveira.willy.starwarsresistance.dto.RebelResponseDto;
import com.oliveira.willy.starwarsresistance.model.Rebel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebelResponseDtoMapper {
    RebelResponseDto rebelToRebelResponseDto(Rebel rebel);
}
