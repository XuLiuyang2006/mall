package com.example.mall.service;

import com.example.mall.dto.OrderDTO;
import com.example.mall.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void cancelOrder(Long id);
    void deleteOrder(Long id);
    List<Order> listOrdersByUser(Long userId);
    Page<Order> listOrdersByMe(Long userId);
    void payOrder(Long orderId,Long userId);//支付订单,参数为订单ID和用户传入ID
}