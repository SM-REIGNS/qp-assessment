package com.questionpro.groceryapp.repository;

import com.questionpro.groceryapp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}