package com.example.mall.service.impl;

import com.example.mall.dto.OrderDTO;
import com.example.mall.entity.Order;
import com.example.mall.entity.OrderItem;
import com.example.mall.entity.Product;
import com.example.mall.entity.User;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.OrderItemRepository;
import com.example.mall.repository.OrderRepository;
import com.example.mall.repository.ProductRepository;
import com.example.mall.repository.UserRepository;
import com.example.mall.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Order createOrder(OrderDTO dto) {
        double total = 0.0;
        List<OrderItem> itemList = new ArrayList<>();

        for (OrderDTO.Item item : dto.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BizException(ResultCode.PRODUCT_NOT_FOUND));

            if (product.getStock() < item.getQuantity()) {
                throw new BizException(ResultCode.STOCK_NOT_ENOUGH,product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            itemList.add(orderItem);

            total += product.getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setCreatedTime(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : itemList) {
            item.setOrderId(savedOrder.getId());
        }

        orderItemRepository.saveAll(itemList);
        return savedOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.NOT_FOUND));
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)//查询订单是否存在
                .orElseThrow(() -> new BizException(ResultCode.NOT_FOUND));

        if (!"PENDING".equals(order.getStatus())) {//检查订单状态是否支持撤销
            throw new BizException(ResultCode.ORDER_CANNOT_CANCEL);
        }

        // 1. 修改订单状态
        order.setStatus("CANCELLED");
        orderRepository.save(order);

        // 2. 查找该订单的所有商品项
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        // 3. 恢复商品库存
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId())//查询商品是否存在
                    .orElseThrow(() -> new BizException(ResultCode.PRODUCT_NOT_FOUND));
            product.setStock(product.getStock() + item.getQuantity());//恢复商品的库存
            productRepository.save(product);
        }
    }



    @Override
    @Transactional
    public void deleteOrder(Long id){
        //先查询订单是否存在
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.ORDER_NOT_FOUND));
        //接着删除订单
        orderRepository.deleteById(id);
    }

    @Override//实现管理员查询接口
    public List<Order> listOrdersByUser(Long userId){
        User user = userRepository.findById(userId)//先检查用户是否存在
                .orElseThrow(()->new BizException(ResultCode.USER_NOT_FOUND));
        //身份验证在Controller中实现
        //接着查询订单
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Page<Order> listOrdersByMe(Long userId){
        // 分页参数固定：第0页，每页10条，按创建时间降序
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdTime"));

        return orderRepository.findByUserId(userId,pageable);
    }

    @Override
    @Transactional
    public void payOrder(Long orderId,Long userId){//支付订单,参数为订单ID和用户传入ID(用户自己的id)
        Order order = orderRepository.findById(orderId)//查询订单是否存在
                .orElseThrow(() -> new BizException(ResultCode.ORDER_NOT_FOUND));

        if (!order.getUserId().equals(userId)) {//检查订单是否属于当前用户
            throw new BizException(ResultCode.UNAUTHORIZED);
        }

        if (!"PENDING".equals(order.getStatus())) {//检查订单状态是否支持支付
            throw new BizException(ResultCode.ORDER_CANNOT_PAY,"当前状态: " + order.getStatus());
        }

        order.setStatus("PAID");
        order.setPayTime(LocalDateTime.now());
        orderRepository.save(order);
    }

}
