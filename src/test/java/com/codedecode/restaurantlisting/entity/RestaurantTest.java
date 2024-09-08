package com.codedecode.restaurantlisting.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");
    }

    @Test
    void testAllArgsConstructor() {
        Restaurant restaurant = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");

        assertEquals(1, restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test St", restaurant.getAddress());
        assertEquals("Test City", restaurant.getCity());
        assertEquals("A test restaurant", restaurant.getRestaurantDescription());
    }

    @Test
    void testNoArgsConstructor() {
        Restaurant restaurant = new Restaurant();
        assertNotNull(restaurant);  // Just testing that the object can be created.
    }

    @Test
    void testGettersAndSetters() {
        restaurant.setId(2);
        restaurant.setName("Updated Restaurant");
        restaurant.setAddress("Updated Address");
        restaurant.setCity("Updated City");
        restaurant.setRestaurantDescription("Updated Description");

        assertEquals(2, restaurant.getId());
        assertEquals("Updated Restaurant", restaurant.getName());
        assertEquals("Updated Address", restaurant.getAddress());
        assertEquals("Updated City", restaurant.getCity());
        assertEquals("Updated Description", restaurant.getRestaurantDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        Restaurant restaurant1 = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");
        Restaurant restaurant2 = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");

        assertEquals(restaurant1, restaurant2);
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Restaurant(id=1, name=Test Restaurant, address=123 Test St, city=Test City, restaurantDescription=A test restaurant)";
        assertEquals(expectedString, restaurant.toString());
    }
}
