package com.oliveira.willy.starwarsresistence.mapper;

import com.oliveira.willy.starwarsresistence.dto.LocalizationDto;
import com.oliveira.willy.starwarsresistence.model.Localization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalizationMapper {
    Localization localizationDTOToLocalization(LocalizationDto localizationDto);
}
