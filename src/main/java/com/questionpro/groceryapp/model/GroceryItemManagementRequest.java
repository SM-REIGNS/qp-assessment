package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GroceryItemManagementRequest implements Serializable {
    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private String price;

    @JsonProperty("inventoryCount")
    private Integer inventoryCount;
}
