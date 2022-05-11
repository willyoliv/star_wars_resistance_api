package com.oliveira.willy.starwarsresistance.mapper;

import com.oliveira.willy.starwarsresistance.dto.LocationDto;
import com.oliveira.willy.starwarsresistance.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationDTOToLocation(LocationDto locationDto);
}
