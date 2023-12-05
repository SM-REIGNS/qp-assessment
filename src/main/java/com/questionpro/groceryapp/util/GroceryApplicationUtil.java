package com.questionpro.groceryapp.util;

import com.questionpro.groceryapp.constant.Operation;
import com.questionpro.groceryapp.constant.Role;
import com.questionpro.groceryapp.model.GroceryItemManagementRequest;
import com.questionpro.groceryapp.model.InventoryManagementRequest;
import com.questionpro.groceryapp.model.OrderCreationRequest;
import com.questionpro.groceryapp.model.UserCreationRequest;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

public class GroceryApplicationUtil {

    public static boolean isValidRequest(Object request) {
        if (request instanceof UserCreationRequest)
        {
            return EnumUtils.isValidEnum(Role.class, ((UserCreationRequest) request).getRole());
        } else if (request instanceof GroceryItemManagementRequest) {
            return !StringUtils.isAnyBlank(((GroceryItemManagementRequest) request).getName(), ((GroceryItemManagementRequest) request).getPrice()) && Objects.nonNull(((GroceryItemManagementRequest) request).getInventoryCount());
        } else if (request instanceof InventoryManagementRequest) {
            return EnumUtils.isValidEnum(Operation.class, ((InventoryManagementRequest) request).getOperation()) && Objects.nonNull(((InventoryManagementRequest) request).getQuantity());
        } else if (request instanceof OrderCreationRequest) {
            return Objects.nonNull(((OrderCreationRequest) request).getUserId()) && CollectionUtils.isEmpty(((OrderCreationRequest) request).getItems()) && StringUtils.isBlank(((OrderCreationRequest) request).getTotalAmount());
        }
        return true;
    }
}
