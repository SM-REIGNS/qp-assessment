package com.questionpro.groceryapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grocery_item")
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "price", nullable = false, length = 20)
    private String price;

    @Column(name = "inventory_count", nullable = false)
    private Integer inventoryCount;
}