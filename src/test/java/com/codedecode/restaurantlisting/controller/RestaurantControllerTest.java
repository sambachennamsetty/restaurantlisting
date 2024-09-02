package com.codedecode.restaurantlisting.controller;


import com.codedecode.restaurantlisting.dto.RestaurantDTO;
import com.codedecode.restaurantlisting.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;


    @Test
    public void testFetchAllRestaurants() throws Exception {

        // Create a mock restaurants
        List<RestaurantDTO> mockRestaurants = Arrays.asList(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1"), new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Description 2"));

        // Mock the service behavior
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

        mockMvc.perform(get("/restaurant/fetchAllRestaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockRestaurants.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(mockRestaurants.get(0).getName()))
                .andExpect(jsonPath("$[0].address").value(mockRestaurants.get(0).getAddress()))
                .andExpect(jsonPath("$[0].city").value(mockRestaurants.get(0).getCity()))
                .andExpect(jsonPath("$[0].restaurantDescription").value(mockRestaurants.get(0).getRestaurantDescription()));


    }

    @Test
    public void testCreateRestaurant() throws Exception {

        RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1");
        when(restaurantService.createRestaurant(any(RestaurantDTO.class))).thenReturn(mockRestaurant);

        mockMvc.perform(post("/restaurant/addRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRestaurant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockRestaurant.getId()))
                .andExpect(jsonPath("$.name").value(mockRestaurant.getName()))
                .andExpect(jsonPath("$.address").value(mockRestaurant.getAddress()))
                .andExpect(jsonPath("$.city").value(mockRestaurant.getCity()))
                .andExpect(jsonPath("$.restaurantDescription").value(mockRestaurant.getRestaurantDescription()));
    }

    @Test
    public void testFindRestaurantById() throws Exception {

        // Create a mock restaurant to be saved
        RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1");
        ResponseEntity<RestaurantDTO> mockResponse = new ResponseEntity<>(mockRestaurant, HttpStatus.OK);

        // Mock the service behavior
        when(restaurantService.findRestaurantByID(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/restaurant/fetchById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockRestaurant.getId()))
                .andExpect(jsonPath("$.name").value(mockRestaurant.getName()))
                .andExpect(jsonPath("$.address").value(mockRestaurant.getAddress()))
                .andExpect(jsonPath("$.city").value(mockRestaurant.getCity()))
                .andExpect(jsonPath("$.restaurantDescription").value(mockRestaurant.getRestaurantDescription()));
    }
}
