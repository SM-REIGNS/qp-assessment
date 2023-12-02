package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Item implements Serializable {

    @JsonProperty("itemId")
    private Integer id;

    @JsonProperty("quantity")
    private Integer quantity;
}
