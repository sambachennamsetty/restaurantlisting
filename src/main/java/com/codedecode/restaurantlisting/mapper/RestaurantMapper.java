package com.codedecode.restaurantlisting.mapper;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO);

    // Basic Mapping
    RestaurantDTO mapRestaurantToRestaurantDto(Restaurant restaurant);

    // mapping with different property names
    /*    @Mapping(source = "firstName", target = "name")
        UserDTO toUserDTO(User user);*/

    // nested mapping
    /*  @Mapping(source = "address.street", target = "streetName")
    UserDTO toUserDTO(User user);*/
}
