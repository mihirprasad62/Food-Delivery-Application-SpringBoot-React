package com.kodewala.service.impl;

import com.kodewala.entity.CartEntity;
import com.kodewala.io.CartRequest;
import com.kodewala.io.CartResponse;
import com.kodewala.repository.CartRepository;
import com.kodewala.service.CartService;
import com.kodewala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId=userService.findByUserId();
        Optional<CartEntity> cartOptional =cartRepository.findByUserId(loggedInUserId);
        CartEntity cart=  cartOptional.orElseGet(()->new CartEntity(loggedInUserId,new HashMap<>()));
        Map<String,Integer> cartItems= cart.getItems();
        cartItems.put(request.getFoodId(),cartItems.getOrDefault(request.getFoodId(),0)+1);
        cart.setItems(cartItems);
       cart= cartRepository.save(cart);
       return convertToResponse(cart);
    }

    private CartResponse convertToResponse(CartEntity cartEntity){
       return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}
