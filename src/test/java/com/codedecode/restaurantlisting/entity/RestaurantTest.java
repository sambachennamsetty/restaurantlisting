package com.codedecode.restaurantlisting.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test restaurant");
    }

    @Test
    void testAllArgsConstructor() {
        Restaurant rest = new Restaurant(1, "Test Restaurant", "123 Test St", "Test City", "A test rest");

        assertEquals(1, rest.getId());
        assertEquals("Test Restaurant", rest.getName());
        assertEquals("123 Test St", rest.getAddress());
        assertEquals("Test City", rest.getCity());
        assertEquals("A test rest", rest.getRestaurantDescription());
    }

    @Test
    void testNoArgsConstructor() {
        Restaurant restau = new Restaurant();
        assertNotNull(restau);  // Verifies that an instance can be created with no arguments.
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

        // Same values
        assertEquals(restaurant1, restaurant2);
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());

        // Different ID
        restaurant2.setId(2);
        assertNotEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1.hashCode(), restaurant2.hashCode());

        // Null comparison
        assertNotEquals(null, restaurant1);

        // Different object type comparison
        assertNotEquals(restaurant1, new Object());
    }

    @Test
    void testEqualsWithNullValues() {
        Restaurant restaurant1 = new Restaurant(null, null, null, null, null);
        Restaurant restaurant2 = new Restaurant(null, null, null, null, null);

        // Ensure two empty objects are equal
        assertEquals(restaurant1, restaurant2);
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());

        // Set different values
        restaurant1.setId(1);
        assertNotEquals(restaurant1, restaurant2);
    }

    @Test
    void testToString() {
        String expectedString = "Restaurant(id=1, name=Test Restaurant, address=123 Test St, city=Test City, restaurantDescription=A test restaurant)";
        assertEquals(expectedString, restaurant.toString());
    }
}