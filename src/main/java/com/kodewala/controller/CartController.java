package com.kodewala.controller;


import com.kodewala.io.CartRequest;
import com.kodewala.io.CartResponse;
import com.kodewala.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;


    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
       String foodId= request.getFoodId();
        if(foodId==null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"food Id not found.");
        }
        return cartService.addToCart(request);

    }

    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }
}
