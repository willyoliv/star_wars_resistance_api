package com.oliveira.willy.starwarsresistance.mapper;

import com.oliveira.willy.starwarsresistance.dto.ItemDto;
import com.oliveira.willy.starwarsresistance.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemDTOToItem(ItemDto itemDto);
}
