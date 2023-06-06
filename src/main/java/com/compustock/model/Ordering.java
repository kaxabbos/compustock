package com.compustock.model;

import com.compustock.model.enums.OrderingStatus;
import com.compustock.model.enums.PaymentType;
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
public class Ordering {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int fullPrice;
    private int fullQuantity;
    @Enumerated(EnumType.STRING)
    private OrderingStatus orderingStatus;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private String date;
    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderingDetail> details;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Ordering(String date) {
        this.date = date;
        this.paymentType = PaymentType.FULL;
        this.orderingStatus = OrderingStatus.NOT_RESERVED;
        this.fullQuantity = 0;
        this.fullPrice = 0;
        this.details = new ArrayList<>();
    }

    public void addDetail(OrderingDetail detail) {
        details.add(detail);
        detail.setOrdering(this);
    }

    public void removeDetail(OrderingDetail detail) {
        details.remove(detail);
        detail.setOrdering(null);
    }
}
