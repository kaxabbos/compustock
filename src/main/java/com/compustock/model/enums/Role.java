package com.compustock.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Управляющий складским отделом"),
    MANAGER("Менеджер отдела"),
    EMPLOYEE("Сотрудник организации");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}

