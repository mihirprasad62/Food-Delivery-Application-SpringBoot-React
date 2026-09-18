package com.kodewala.service;

import com.kodewala.io.OrderRequest;
import com.kodewala.io.OrderResponse;
import com.razorpay.RazorpayException;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException, JSONException;

    void verifyPayment(Map<String,String> paymentData,String status);

    List<OrderResponse> getUserOrders();

    void removeOrder(String orderId);

    List<OrderResponse>  getOrdersOfAllUsers();

    void updateOrderStatus(String orderId,String status);
}
