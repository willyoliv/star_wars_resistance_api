package com.oliveira.willy.starwarsresistance.mapper;


import com.oliveira.willy.starwarsresistance.dto.LocationResponseDto;
import com.oliveira.willy.starwarsresistance.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationResponseDtoMapper {

    LocationResponseDto locationToLocationResponseDto(Location location);
}
