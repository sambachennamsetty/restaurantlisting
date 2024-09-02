package com.codedecode.restaurantlisting.service;

import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.entity.Restaurant;
import com.codedecode.restaurantlisting.mapper.RestaurantMapper;
import com.codedecode.restaurantlisting.repo.RestaurantRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @Mock
    private RestaurantRepo restaurantRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllRestaurants() {

        List<Restaurant> mockRestaurants = Arrays.asList(new Restaurant(1, "Restaurant 1", "Address 1", "City 1", "Restaurant Description"),
                new Restaurant(1, "Restaurant 1", "Address 1", "City 1", "Restaurant Description"));

        when(restaurantRepo.findAll()).thenReturn(mockRestaurants);

        // call the service method
        List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

        // verify the result
        assertEquals(mockRestaurants.size(), restaurantDTOList.size());
        for(int i =0; i< mockRestaurants.size(); i++) {
            RestaurantDTO expectedDto = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDto(mockRestaurants.get(i));
            assertEquals(expectedDto, restaurantDTOList.get(i));
        }

        // verify that the repository method was called
        verify(restaurantRepo, times(1)).findAll();
    }

    @Test
    public void testCreateRestaurant() {

        RestaurantDTO mockRestaurantDTO = new RestaurantDTO(1, "Restaurant 1", "Address 1", "City 1", "Restaurant Description");
        Restaurant mockRestaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);

        when(restaurantRepo.save(mockRestaurant)).thenReturn(mockRestaurant);

        // call the service method
        RestaurantDTO savedRestaurantDto = restaurantService.createRestaurant(mockRestaurantDTO);

        // verify the result
        assertEquals(mockRestaurantDTO, savedRestaurantDto);

        // verify that the repository method was called
        verify(restaurantRepo, times(1)).save(mockRestaurant);
    }

    @Test
    public void testFindRestaurantByIDWhenExists() {

        Integer mockRestaurantId = 1;

        Restaurant mockRestaurant = new Restaurant(1, "Restaurant 1", "Address 1", "City 1", "Restaurant Description");
        Optional<Restaurant> optionalRestaurant = Optional.of(mockRestaurant);

        when(restaurantRepo.findById(1)).thenReturn(optionalRestaurant);

        // call the service method
        ResponseEntity<RestaurantDTO> response = restaurantService.findRestaurantByID(mockRestaurantId);

        // verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurantId ,response.getBody().getId());

        // verify that the repository method was called
        verify(restaurantRepo, times(1)).findById(mockRestaurantId);
    }

    @Test
    public void testFindRestaurantByIDWhenNotExists() {

        Integer mockRestaurantId = 1;

        Optional<Restaurant> optionalRestaurant = Optional.empty();

        when(restaurantRepo.findById(1)).thenReturn(optionalRestaurant);

        // call the service method
        ResponseEntity<RestaurantDTO> response = restaurantService.findRestaurantByID(mockRestaurantId);

        // verify the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // verify that the repository method was called
        verify(restaurantRepo, times(1)).findById(mockRestaurantId);
    }
}
