package com.questionpro.groceryapp.service.impl;

import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.entity.Order;
import com.questionpro.groceryapp.entity.OrderItem;
import com.questionpro.groceryapp.exception.ResourceNotFoundException;
import com.questionpro.groceryapp.model.Item;
import com.questionpro.groceryapp.model.OrderCreationRequest;
import com.questionpro.groceryapp.repository.GroceryItemRepository;
import com.questionpro.groceryapp.repository.OrderItemRepository;
import com.questionpro.groceryapp.repository.OrderRepository;
import com.questionpro.groceryapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public void addOrder(OrderCreationRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalAmount(request.getTotalAmount());
        order = orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        List<GroceryItem> groceryItems = new ArrayList<>();
        for (Item item: request.getItems())
        {
            GroceryItem groceryItem = groceryItemRepository.findById(item.getId()).orElseThrow(()-> new ResourceNotFoundException("Item with id "+item.getId()+" not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(item.getId());
            orderItem.setQuantity(item.getQuantity());
            groceryItem.setInventoryCount(groceryItem.getInventoryCount() - item.getQuantity());
            orderItems.add(orderItem);
            groceryItems.add(groceryItem);
        }
        groceryItemRepository.saveAll(groceryItems);
        orderItemRepository.saveAll(orderItems);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersForUser(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders;
    }
}
