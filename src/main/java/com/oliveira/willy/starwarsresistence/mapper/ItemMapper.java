package com.oliveira.willy.starwarsresistence.mapper;

import com.oliveira.willy.starwarsresistence.dto.ItemDto;
import com.oliveira.willy.starwarsresistence.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemDTOToItem(ItemDto itemDto);
}
