package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class InventoryManagementRequest implements Serializable {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("quantity")
    private Integer quantity;
}
