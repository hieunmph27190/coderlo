package com.hieucoder.coderlo.constant;

import lombok.Getter;

@Getter
public enum PredefinedPermission {
    EDIT("EDIT","Chỉnh sửa dữ liệu"),
    VIEW("VIEW","Xem dữ liệu")

    ;

    PredefinedPermission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private final String name;
    private final String description;

}
