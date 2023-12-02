package com.questionpro.groceryapp.service;

import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.model.GroceryItemManagementRequest;
import com.questionpro.groceryapp.model.InventoryManagementRequest;
import com.questionpro.groceryapp.model.UserCreationRequest;

import java.util.List;

public interface AdminService {

    void addUser(UserCreationRequest request);

    void addItems(GroceryItemManagementRequest request);

    List<GroceryItem> getAllItems();

    void updateItem(Integer itemId, GroceryItemManagementRequest request);

    void deleteItem(Integer itemId);

    void manageInventory(Integer itemId, InventoryManagementRequest request);
}
