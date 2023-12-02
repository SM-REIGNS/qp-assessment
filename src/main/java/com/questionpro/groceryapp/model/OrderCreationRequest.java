package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class OrderCreationRequest implements Serializable {
    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("items")
    private List<Item> items;

    @JsonProperty("totalAmount")
    private String totalAmount;
}
