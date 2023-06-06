package com.compustock.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatus {
    ALL("Все"),
    PRODUCED("Произведено"),
    RESERVED("Зарезервировано"),
    SHIPPED("Отгружено");

    private final String name;
}
