package com.questionpro.groceryapp.service;


import com.questionpro.groceryapp.entity.Order;
import com.questionpro.groceryapp.model.OrderCreationRequest;

import java.util.List;

public interface OrderService {
    void addOrder(OrderCreationRequest request);

    List<Order> getAllOrdersForUser(Integer userId);
}
