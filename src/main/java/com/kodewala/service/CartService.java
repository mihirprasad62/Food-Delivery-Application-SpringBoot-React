package com.kodewala.service;

import com.kodewala.io.CartRequest;
import com.kodewala.io.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getCart();
}
