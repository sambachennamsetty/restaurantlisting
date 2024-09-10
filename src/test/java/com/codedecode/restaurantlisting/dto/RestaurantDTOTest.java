package com.codedecode.restaurantlisting.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDTOTest {

    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        restaurantDTO = new RestaurantDTO(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");
    }

    @Test
    void testAllArgsConstructor() {
        RestaurantDTO restaurantDTO = new RestaurantDTO(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");

        assertEquals(1, restaurantDTO.getId());
        assertEquals("Test Restaurant", restaurantDTO.getName());
        assertEquals("123 Test St", restaurantDTO.getAddress());
        assertEquals("Test City", restaurantDTO.getCity());
        assertEquals("A test restaurant", restaurantDTO.getRestaurantDescription());
    }

    @Test
    void testNoArgsConstructor() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        assertNotNull(restaurantDTO);  // Verifies that an instance can be created with no arguments.
    }

    @Test
    void testGettersAndSetters() {
        restaurantDTO.setId(2);
        restaurantDTO.setName("Updated Restaurant");
        restaurantDTO.setAddress("Updated Address");
        restaurantDTO.setCity("Updated City");
        restaurantDTO.setRestaurantDescription("Updated Description");

        assertEquals(2, restaurantDTO.getId());
        assertEquals("Updated Restaurant", restaurantDTO.getName());
        assertEquals("Updated Address", restaurantDTO.getAddress());
        assertEquals("Updated City", restaurantDTO.getCity());
        assertEquals("Updated Description", restaurantDTO.getRestaurantDescription());
    }

    // Enhanced Equals and HashCode Test
    @Test
    void testEqualsAndHashCode() {
        RestaurantDTO restaurantDTO1 = new RestaurantDTO(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");
        RestaurantDTO restaurantDTO2 = new RestaurantDTO(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");

        // Same values
        assertEquals(restaurantDTO1, restaurantDTO2);
        assertEquals(restaurantDTO1.hashCode(), restaurantDTO2.hashCode());

        // Different ID
        restaurantDTO2.setId(2);
        assertNotEquals(restaurantDTO1, restaurantDTO2);
        assertNotEquals(restaurantDTO1.hashCode(), restaurantDTO2.hashCode());

        // Null comparison
        assertNotEquals(restaurantDTO1, null);

        // Different object type comparison
        assertNotEquals(restaurantDTO1, new Object());
    }

    @Test
    void testEqualsWithNullValues() {
        RestaurantDTO restaurantDTO1 = new RestaurantDTO(null, null, null, null, null);
        RestaurantDTO restaurantDTO2 = new RestaurantDTO(null, null, null, null, null);

        // Ensure two empty objects are equal
        assertEquals(restaurantDTO1, restaurantDTO2);
        assertEquals(restaurantDTO1.hashCode(), restaurantDTO2.hashCode());

        // Set different values
        restaurantDTO1.setId(1);
        assertNotEquals(restaurantDTO1, restaurantDTO2);
    }

    @Test
    void testToString() {
        String expectedString = "RestaurantDTO(id=1, name=Test Restaurant, address=123 Test St, city=Test City, restaurantDescription=A test restaurant)";
        assertEquals(expectedString, restaurantDTO.toString());
    }
}
