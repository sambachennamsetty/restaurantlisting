package com.codedecode.restaurantlisting.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Integer id;
    private String name;
    private String address;
    private String city;
    private String restaurantDescription;
}
