package com.questionpro.groceryapp.controller;

import com.questionpro.groceryapp.constant.Constants;
import com.questionpro.groceryapp.constant.ResponseStatus;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.entity.Order;
import com.questionpro.groceryapp.exception.BadRequestException;
import com.questionpro.groceryapp.exception.ResourceNotFoundException;
import com.questionpro.groceryapp.model.BaseResponse;
import com.questionpro.groceryapp.model.Meta;
import com.questionpro.groceryapp.model.OrderCreationRequest;
import com.questionpro.groceryapp.service.AdminService;
import com.questionpro.groceryapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constants.API_BASE_URI+"/user")
public class UserController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get available grocery item(s)")
    @GetMapping("/grocery-items")
    public ResponseEntity<BaseResponse> getGroceryItems() {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            List<GroceryItem> groceryItems = adminService.getAllItems();
            baseResponse = new BaseResponse(new Meta("200", com.questionpro.groceryapp.constant.ResponseStatus.SUCCESS, errors, Strings.EMPTY), groceryItems);
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", com.questionpro.groceryapp.constant.ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (ResourceNotFoundException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("404", com.questionpro.groceryapp.constant.ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @Operation(summary = "Place an order of grocery items")
    @PostMapping("/orders/create")
    public ResponseEntity<BaseResponse> createOrder(@RequestBody OrderCreationRequest request) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            orderService.addOrder(request);
            baseResponse = new BaseResponse(new Meta("201", ResponseStatus.SUCCESS, errors, Constants.USER_ADD_ORDER_SUCCESS_MESSAGE));
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @Operation(summary = "View Order History")
    @GetMapping("/orders/{userId}")
    public ResponseEntity<BaseResponse> getOrdersByUser(@PathVariable("userId") Integer userId) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            List<Order> orders = orderService.getAllOrdersForUser(userId);
            baseResponse = new BaseResponse(new Meta("200", ResponseStatus.SUCCESS, errors, Strings.EMPTY), orders);
        } catch (ResourceNotFoundException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("404", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
