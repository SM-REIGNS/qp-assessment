package com.questionpro.groceryapp.service.impl;

import com.google.gson.Gson;
import com.questionpro.groceryapp.constant.Operation;
import com.questionpro.groceryapp.constant.Role;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.entity.User;
import com.questionpro.groceryapp.exception.BadRequestException;
import com.questionpro.groceryapp.exception.JPAException;
import com.questionpro.groceryapp.exception.ResourceNotFoundException;
import com.questionpro.groceryapp.model.GroceryItemManagementRequest;
import com.questionpro.groceryapp.model.InventoryManagementRequest;
import com.questionpro.groceryapp.model.UserCreationRequest;
import com.questionpro.groceryapp.repository.GroceryItemRepository;
import com.questionpro.groceryapp.repository.OrderRepository;
import com.questionpro.groceryapp.repository.UserRepository;
import com.questionpro.groceryapp.service.AdminService;
import com.questionpro.groceryapp.util.GroceryApplicationUtil;
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

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addUser(UserCreationRequest request) {
        if (!GroceryApplicationUtil.isValidRequest(request))
        {
            throw new BadRequestException("Invalid Request : " + new Gson().toJson(request));
        }
        User user = new User();
        user.setRole(Role.valueOf(request.getRole()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new JPAException("Exception while trying to add a new user : "+ e);
        }
    }

    @Override
    public void addItems(GroceryItemManagementRequest request) {
        if (!GroceryApplicationUtil.isValidRequest(request))
        {
            throw new BadRequestException("Invalid Request : "+new Gson().toJson(request));
        }
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName(request.getName());
        groceryItem.setPrice(request.getPrice());
        groceryItem.setInventoryCount(request.getInventoryCount());
        try {
            groceryItemRepository.save(groceryItem);
        } catch (Exception e) {
            throw new JPAException("Exception while trying to add a new item : "+ e);
        }
    }

    @Override
    public List<GroceryItem> getAllItems() {
        List<GroceryItem> groceryItems = groceryItemRepository.findAll();
        if (groceryItems.isEmpty())
        {
            throw new ResourceNotFoundException("No grocery items found");
        }
        return groceryItems;
    }

    @Override
    public void updateItem(Integer itemId, GroceryItemManagementRequest request) {
        if (!GroceryApplicationUtil.isValidRequest(request) || Objects.isNull(itemId))
        {
            throw new BadRequestException("Invalid Request : "+new Gson().toJson(request)+" ItemId : "+itemId);
        }
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            GroceryItem groceryItem = groceryItemOptional.get();
            groceryItem.setName(request.getName().isBlank()?groceryItem.getName():request.getName());
            groceryItem.setPrice(request.getPrice().isBlank()? groceryItem.getPrice():request.getPrice());
            groceryItem.setInventoryCount(Objects.isNull(request.getInventoryCount())? groceryItem.getInventoryCount():Integer.valueOf(request.getInventoryCount()));
            try {
                groceryItemRepository.save(groceryItem);
            } catch (Exception e) {
                throw new JPAException("Exception while trying to update an item : "+ e);
            }
        }
        else {
            throw new ResourceNotFoundException("Item with itemId "+itemId+" not found");
        }
    }

    @Override
    public void deleteItem(Integer itemId) {
        if (Objects.isNull(itemId))
        {
            throw new BadRequestException("Invalid itemId : "+itemId);
        }
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            try {
                groceryItemRepository.deleteById(itemId);
            } catch (Exception e) {
                throw new JPAException("Exception while trying to delete an item : "+ e);
            }
        }
        else {
            throw  new ResourceNotFoundException("Item with itemId "+itemId+" not found");
        }
    }

    @Override
    public void manageInventory(Integer itemId, InventoryManagementRequest request) {
        if (!GroceryApplicationUtil.isValidRequest(request) || Objects.isNull(itemId))
        {
            throw new BadRequestException("Invalid Request : "+new Gson().toJson(request)+" ItemId : "+itemId);
        }
        Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(itemId);
        if (groceryItemOptional.isPresent())
        {
            GroceryItem groceryItem = groceryItemOptional.get();
            groceryItem.setInventoryCount(request.getOperation().equals(Operation.INCREASE.name())?groceryItem.getInventoryCount() + request.getQuantity():groceryItem.getInventoryCount() - request.getQuantity());
            groceryItemRepository.save(groceryItem);
        }
        else {
            throw  new ResourceNotFoundException("Item with itemId "+itemId+" not found");
        }
    }
}
