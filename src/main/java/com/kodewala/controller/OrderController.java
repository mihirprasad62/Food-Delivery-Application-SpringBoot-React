package com.kodewala.controller;

import com.kodewala.io.OrderRequest;
import com.kodewala.io.OrderResponse;
import com.kodewala.service.OrderService;
import com.razorpay.RazorpayException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws JSONException, RazorpayException {
      OrderResponse response = orderService.createOrderWithPayment(request);
      return response;
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String,String> paymentData){
        orderService.verifyPayment(paymentData,"paid");
    }

    @GetMapping
    public List<OrderResponse> getOrders(){
      return  orderService.getUserOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
    }

    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers(){
        return orderService.getOrdersOfAllUsers();
    }

    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@RequestParam String status,@PathVariable String orderId){
         orderService.updateOrderStatus(orderId,status);
    }
}
