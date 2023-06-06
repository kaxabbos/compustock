package com.compustock.model;

import com.compustock.model.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StatProduct {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;
    private String date;
    private Long idOrderingDetail;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public StatProduct(int quantity, String date, Long idOrderingDetail, ProductStatus productStatus) {
        this.quantity = quantity;
        this.date = date;
        this.idOrderingDetail = idOrderingDetail;
        this.productStatus = productStatus;
    }
}
