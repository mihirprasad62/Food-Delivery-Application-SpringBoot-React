package com.kodewala.service.impl;

import com.kodewala.entity.OrderEntity;
import com.kodewala.io.OrderRequest;
import com.kodewala.io.OrderResponse;
import com.kodewala.repository.CartRepository;
import com.kodewala.repository.OrderRepository;
import com.kodewala.service.AuthenticationFacade;
import com.kodewala.service.OrderService;
import com.kodewala.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    CartRepository cartRepository;

    @Value("${razorpay.api.key}")
    private String razorpayKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;



    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException, JSONException {
        OrderEntity newOrder=convertToEntity(request);
        newOrder=orderRepository.save(newOrder);

        //CREATE RAZORPAY PAYMENT ORDER

        RazorpayClient razorpayClient=new RazorpayClient(razorpayKey,razorpaySecret);

        JSONObject orderRequest=new JSONObject();
        orderRequest.put("amount",newOrder.getAmount());
        orderRequest.put("currency","INR");
        orderRequest.put("payment_capture",1);

        Order razorpayOrder =razorpayClient.orders.create(orderRequest);
        newOrder.setRazorpayOrderId(razorpayOrder.get("id"));

        String loggedInUserId=userService.findByUserId();
        newOrder.setUserId(loggedInUserId);
        newOrder=orderRepository.save(newOrder);

        return converToResponse(newOrder);



    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
       String razorpayOrderId= paymentData.get("razorpay_order_id");
       OrderEntity existingOrder=orderRepository.findByRazorpayOrderId(razorpayOrderId)
               .orElseThrow(()->new RuntimeException("Order not found"));

       existingOrder.setPaymentStatus(status);
       existingOrder.setRazorpaySigneture(paymentData.get("razorpay_signature"));
       existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
       orderRepository.save(existingOrder);

       if("paid".equalsIgnoreCase(status)){
           cartRepository.deleteByUserId(existingOrder.getUserId());
       }

    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId=userService.findByUserId();
        List <OrderEntity> listOfOrders =orderRepository.findByUserId(loggedInUserId);
      return  listOfOrders.stream().map(entity->converToResponse(entity)).collect(Collectors.toList());

    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
       List<OrderEntity> orders=orderRepository.findAll();
      return orders.stream().map(entity->converToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        OrderEntity entity=orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order Not Found"));

        entity.setOrderStatus(status);
        orderRepository.save(entity);
    }

    private OrderResponse converToResponse(OrderEntity newOrder) {
      return  OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .userId(newOrder.getUserId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .paymentStatus(newOrder.getPaymentStatus())
                .orderStatus(newOrder.getOrderStatus())
                .email(newOrder.getEmail())
                 .phoneNumber(newOrder.getPhoneNumber())
              .orderedItems(newOrder.getOrderedItems())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
       return OrderEntity.builder()

                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderedItems(request.getOrderedItems())
               .email(request.getEmail())
               .phoneNumber(request.getPhoneNumber())
               .orderStatus(request.getOrderStatus())
                .build();
    }
}
