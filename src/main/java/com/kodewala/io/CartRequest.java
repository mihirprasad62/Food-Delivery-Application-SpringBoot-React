package com.kodewala.io;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartRequest {
   // private String userId;
   // private Map<String,Integer> items=new HashMap<>();

    private String foodId;
}
