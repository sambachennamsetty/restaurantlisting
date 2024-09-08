package com.codedecode.restaurantlisting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantlistingApplicationTests {

    @Test
    void contextLoads() {
        // This test will pass if the application context is loaded successfully
    }
}
