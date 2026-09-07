package com.kodewala.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {

    private String id;

    private String name;

    private String description;
    private double price;
    private String category;

    private String imageUrl;
}
