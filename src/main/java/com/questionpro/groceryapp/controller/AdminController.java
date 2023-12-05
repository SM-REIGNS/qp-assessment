package com.questionpro.groceryapp.controller;

import com.questionpro.groceryapp.constant.Constants;
import com.questionpro.groceryapp.constant.ResponseStatus;
import com.questionpro.groceryapp.entity.GroceryItem;
import com.questionpro.groceryapp.exception.BadRequestException;
import com.questionpro.groceryapp.exception.ResourceNotFoundException;
import com.questionpro.groceryapp.model.*;
import com.questionpro.groceryapp.service.AdminService;
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
@RequestMapping(Constants.API_BASE_URI+"/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Add a new user")
    @PostMapping("/users/add")
    public ResponseEntity<BaseResponse> addUser(@RequestBody UserCreationRequest request) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            adminService.addUser(request);
            baseResponse = new BaseResponse(new Meta("201", ResponseStatus.SUCCESS, errors, Constants.ADMIN_ADD_USER_SUCCESS_MESSAGE));
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


    @Operation(summary = "Add new grocery item(s)")
    @PostMapping("/grocery-items/add")
    public ResponseEntity<BaseResponse> addGroceryItems(@RequestBody GroceryItemManagementRequest request) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            adminService.addItems(request);
            baseResponse = new BaseResponse(new Meta("201", ResponseStatus.SUCCESS, errors, Constants.ADMIN_ADD_ITEM_SUCCESS_MESSAGE));
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

    @Operation(summary = "Get all grocery item(s)")
    @GetMapping("/grocery-items/get")
    public ResponseEntity<BaseResponse> getGroceryItems() {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            List<GroceryItem> groceryItems = adminService.getAllItems();
            baseResponse = new BaseResponse(new Meta("200", ResponseStatus.SUCCESS, errors, Strings.EMPTY), groceryItems);
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
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

    @Operation(summary = "Remove a grocery item")
    @DeleteMapping("/grocery-items/{itemId}/delete")
    public ResponseEntity<BaseResponse> removeGroceryItem(@PathVariable("itemId") Integer itemId) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            adminService.deleteItem(itemId);
            baseResponse = new BaseResponse(new Meta("204", ResponseStatus.SUCCESS, errors, Constants.ADMIN_REMOVE_ITEM_SUCCESS_MESSAGE));
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (ResourceNotFoundException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("404", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(baseResponse);
    }

    @Operation(summary = "Update details of an existing grocery item")
    @PutMapping("/grocery-items/{itemId}/update")
    public ResponseEntity<BaseResponse> updateGroceryItem(@PathVariable("itemId") Integer itemId, @RequestBody GroceryItemManagementRequest request) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            adminService.updateItem(itemId, request);
            baseResponse = new BaseResponse(new Meta("204", ResponseStatus.SUCCESS, errors, Constants.ADMIN_REMOVE_ITEM_SUCCESS_MESSAGE));
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (ResourceNotFoundException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("404", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);

        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(baseResponse);
    }

    @Operation(summary = "Update Inventory Details of an existing grocery item")
    @PutMapping("/grocery-items/{itemId}/manage-inventory")
    public ResponseEntity<BaseResponse> manageGroceryInventory(@PathVariable("itemId") Integer itemId, @RequestBody InventoryManagementRequest request) {
        BaseResponse baseResponse;
        List<Object> errors = new ArrayList<>();
        try {
            adminService.manageInventory(itemId, request);
            baseResponse = new BaseResponse(new Meta("204", ResponseStatus.SUCCESS, errors, Constants.ADMIN_REMOVE_ITEM_SUCCESS_MESSAGE));
        } catch (BadRequestException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("400", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (ResourceNotFoundException e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("404", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            errors.add(Map.of("error", e.getMessage()));
            baseResponse = new BaseResponse(new Meta("500", ResponseStatus.FAILURE, errors, Strings.EMPTY));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(baseResponse);
    }
}
