package com.compustock.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OrderingDetail {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int price;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ordering ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderingDetail() {
        this.price = 0;
        this.quantity = 0;
    }
}