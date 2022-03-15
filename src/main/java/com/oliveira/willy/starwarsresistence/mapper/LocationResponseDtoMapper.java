package com.oliveira.willy.starwarsresistence.mapper;


import com.oliveira.willy.starwarsresistence.dto.LocationResponseDto;
import com.oliveira.willy.starwarsresistence.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationResponseDtoMapper {

    LocationResponseDto locationToLocationResponseDto(Location location);
}
