package com.oliveira.willy.starwarsresistance.mapper;

import com.oliveira.willy.starwarsresistance.dto.RebelCreateDto;
import com.oliveira.willy.starwarsresistance.model.Rebel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebelMapper {

    Rebel rebelDTOToRebel(RebelCreateDto rebelCreateDto);
}
