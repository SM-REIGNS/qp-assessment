package com.questionpro.groceryapp.service.impl;

import com.questionpro.groceryapp.constant.Operation;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.entity.User;
import com.questionpro.groceryapp.model.GroceryItemManagementRequest;
import com.questionpro.groceryapp.model.InventoryManagementRequest;
import com.questionpro.groceryapp.model.UserCreationRequest;
import com.questionpro.groceryapp.repository.GroceryItemRepository;
import com.questionpro.groceryapp.repository.UserRepository;
import com.questionpro.groceryapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(UserCreationRequest request) {
        User user = new User();
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    @Override
    public void addItems(GroceryItemManagementRequest request) {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName(request.getName());
        groceryItem.setPrice(request.getPrice());
        groceryItem.setInventoryCount(request.getInventoryCount());
        groceryItemRepository.save(groceryItem);
    }

    @Override
    public List<GroceryItem> getAllItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public void updateItem(Integer itemId, GroceryItemManagementRequest request) {
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            GroceryItem groceryItem = groceryItemOptional.get();
            groceryItem.setName(request.getName().isBlank()?groceryItem.getName():request.getName());
            groceryItem.setPrice(request.getPrice().isBlank()? groceryItem.getPrice():request.getPrice());
            groceryItem.setInventoryCount(Objects.isNull(request.getInventoryCount())? groceryItem.getInventoryCount():Integer.valueOf(request.getInventoryCount()));
            groceryItemRepository.save(groceryItem);
        }
    }

    @Override
    public void deleteItem(Integer itemId) {
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            groceryItemRepository.deleteById(itemId);
        }
    }

    @Override
    public void manageInventory(Integer itemId, InventoryManagementRequest request) {
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            GroceryItem groceryItem = groceryItemOptional.get();
            groceryItem.setInventoryCount(request.getOperation().equals(Operation.INCREASE.name())?groceryItem.getInventoryCount() + request.getQuantity():groceryItem.getInventoryCount() - request.getQuantity());
            groceryItemRepository.save(groceryItem);
        }
    }
}
