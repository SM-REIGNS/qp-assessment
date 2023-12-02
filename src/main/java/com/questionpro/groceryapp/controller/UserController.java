package com.questionpro.groceryapp.controller;

import com.questionpro.groceryapp.constant.Constants;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.entity.Order;
import com.questionpro.groceryapp.model.OrderCreationRequest;
import com.questionpro.groceryapp.service.AdminService;
import com.questionpro.groceryapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_URI+"/user")
public class UserController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get available grocery item(s)")
    @GetMapping("/grocery-items")
    public ResponseEntity<List<GroceryItem>> getGroceryItems() {
        List<GroceryItem> groceryItems = adminService.getAllItems();
        return ResponseEntity.ok(groceryItems);
    }

    @Operation(summary = "Place an order of grocery items")
    @PostMapping("/orders/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreationRequest request) {
        orderService.addOrder(request);
        return ResponseEntity.ok(Constants.USER_ADD_ORDER_SUCCESS_MESSAGE);
    }

    @Operation(summary = "View Order History")
    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable("userId") Integer userId) {
        List<Order> orders = orderService.getAllOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }
}
