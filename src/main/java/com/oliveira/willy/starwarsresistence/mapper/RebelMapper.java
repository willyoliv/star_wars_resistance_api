package com.oliveira.willy.starwarsresistence.mapper;

import com.oliveira.willy.starwarsresistence.dto.RebelCreateDto;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebelMapper {

    Rebel rebelDTOToRebel(RebelCreateDto rebelCreateDto);
}
