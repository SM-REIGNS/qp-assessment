package com.questionpro.groceryapp.controller;

import com.questionpro.groceryapp.constant.Constants;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.model.GroceryItemManagementRequest;
import com.questionpro.groceryapp.model.InventoryManagementRequest;
import com.questionpro.groceryapp.model.UserCreationRequest;
import com.questionpro.groceryapp.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_URI+"/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Add a new user")
    @PostMapping("/users/add")
    public ResponseEntity<String> addUser(@RequestBody UserCreationRequest request) {
        adminService.addUser(request);
        return ResponseEntity.ok(Constants.ADMIN_ADD_USER_SUCCESS_MESSAGE);
    }


    @Operation(summary = "Add new grocery item(s)")
    @PostMapping("/grocery-items/add")
    public ResponseEntity<String> addGroceryItems(@RequestBody GroceryItemManagementRequest request) {
        adminService.addItems(request);
        return ResponseEntity.ok(Constants.ADMIN_ADD_ITEM_SUCCESS_MESSAGE);
    }

    @Operation(summary = "Get all grocery item(s)")
    @GetMapping("/grocery-items/get")
    public ResponseEntity<List<GroceryItem>> getGroceryItems() {
        List<GroceryItem> groceryItems = adminService.getAllItems();
        return ResponseEntity.ok(groceryItems);
    }

    @Operation(summary = "Remove a grocery item")
    @DeleteMapping("/grocery-items/{itemId}/delete")
    public ResponseEntity<String> removeGroceryItem(@PathVariable("itemId") Integer itemId) {
        adminService.deleteItem(itemId);
        return ResponseEntity.ok(Constants.ADMIN_REMOVE_ITEM_SUCCESS_MESSAGE);
    }

    @Operation(summary = "Update details of an existing grocery item")
    @PutMapping("/grocery-items/{itemId}/update")
    public ResponseEntity<String> removeGroceryItem(@PathVariable("itemId") Integer itemId, @RequestBody GroceryItemManagementRequest request) {
        adminService.updateItem(itemId, request);
        return ResponseEntity.ok(Constants.ADMIN_UPDATE_ITEM_SUCCESS_MESSAGE);
    }

    @Operation(summary = "Update Inventory Details of an existing grocery item")
    @PutMapping("/grocery-items/{itemId}/manage-inventory")
    public ResponseEntity<String> removeGroceryItem(@PathVariable("itemId") Integer itemId, @RequestBody InventoryManagementRequest request) {
        adminService.manageInventory(itemId, request);
        return ResponseEntity.ok(Constants.ADMIN_UPDATE_ITEM_SUCCESS_MESSAGE);
    }
}
