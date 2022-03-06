package com.oliveira.willy.starwarsresistence.mapper;

import com.oliveira.willy.starwarsresistence.dto.LocationDto;
import com.oliveira.willy.starwarsresistence.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationDTOToLocation(LocationDto locationDto);
}
