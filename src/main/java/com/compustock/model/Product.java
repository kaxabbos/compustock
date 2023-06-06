package com.compustock.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int quantity;
    private int unitPrice;
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<OrderingDetail> details;
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<StatProduct> stats;

    public Product(String name, int quantity, int unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.details = new ArrayList<>();
        this.stats = new ArrayList<>();
    }

    public void addDetail(OrderingDetail detail) {
        details.add(detail);
        detail.setProduct(this);
    }

    public void removeDetail(OrderingDetail detail) {
        details.remove(detail);
        detail.setProduct(null);
    }

    public void addStat(StatProduct stat) {
        stats.add(stat);
        stat.setProduct(this);
    }

    public void removeStat(StatProduct stat) {
        stats.remove(stat);
        stat.setProduct(null);
    }
}
