package com.codedecode.restaurantlisting.service;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import com.codedecode.restaurantlisting.mapper.RestaurantMapper;
import com.codedecode.restaurantlisting.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        List<RestaurantDTO> restaurantDTOS = restaurants.stream().map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDto(restaurant)).collect(Collectors.toList());
        return restaurantDTOS;
    }

    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {

        Restaurant restaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO);
        restaurant = restaurantRepo.save(restaurant);

        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDto(restaurant);
    }

    public ResponseEntity<RestaurantDTO> findRestaurantByID(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(id);
        if (restaurant.isPresent())
            return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDto(restaurant.get()), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
