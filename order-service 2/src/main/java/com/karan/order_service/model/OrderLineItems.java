package com.karan.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_line_items")
@Data
@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode; //Unique product identifier
    private BigDecimal price;
    private Integer quantity;


    public OrderLineItems(String skuCode, BigDecimal price, Integer quantity) {
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;

    }
    public OrderLineItems(Long id, String skuCode, BigDecimal price, Integer quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

}