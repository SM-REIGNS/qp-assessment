package com.questionpro.groceryapp.repository;

import com.questionpro.groceryapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(@NonNull Integer userId);
}