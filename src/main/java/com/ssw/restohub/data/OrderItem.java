package com.ssw.restohub.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "menuItemId",nullable = false)
    private MenuItem menuItem;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId",nullable = false)
    private Order order;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "pricePerItem",nullable = false)
    private double pricePerItem;

    @Column(name = "subTotal",nullable = false)
    private double subTotal;

}
