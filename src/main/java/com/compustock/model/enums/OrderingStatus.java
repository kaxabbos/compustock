package com.compustock.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    ALL("Все"),
    RESERVED("Зарезервировано"),
    NOT_RESERVED("Не зарезервировано"),
    SHIPMENT("В отгрузке"),
    SHIPPED("Отгружено");

    private final String name;
}
