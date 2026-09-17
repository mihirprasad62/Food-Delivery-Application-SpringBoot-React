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

    @Override
    public CartResponse getCart() {
        String loggedInUserId=userService.findByUserId();
       CartEntity entity= cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null
                ,loggedInUserId,new HashMap<>()));
        return convertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId=userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);

    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId=userService.findByUserId();

        CartEntity entity =cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(()->new RuntimeException("Cart is not found"));
       Map<String,Integer> cartItems =entity.getItems();
       if(cartItems.containsKey(request.getFoodId())){
           int currentQty=cartItems.get(request.getFoodId());
           if(currentQty>0){
               cartItems.put(request.getFoodId(),currentQty-1);
           }else{
               cartItems.remove(request.getFoodId());
           }
         entity=  cartRepository.save(entity);

           return convertToResponse(entity);
       }

        return null;
    }

    private CartResponse convertToResponse(CartEntity cartEntity){
       return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}
