package com.compustock.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
    FULL("Полная"),
    PARTIAL("Частичная"),
    UPON_RECEIPT("При получении");

    private final String name;
}
