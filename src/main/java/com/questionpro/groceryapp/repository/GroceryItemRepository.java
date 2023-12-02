package com.questionpro.groceryapp.repository;

import com.questionpro.groceryapp.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {
}