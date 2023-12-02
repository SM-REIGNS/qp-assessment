package com.questionpro.groceryapp.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "\"order\"")
public class Order {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrderItem> OrderedItems;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false, length = 40)
    private Integer userId;

    @Column(name = "total_amount", nullable = false, length = 20)
    private String totalAmount;

    @Column(name = "order_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime orderDate;
}