package com.example.gira.model.entity.enums;

public enum ProgressEnum {
    OPEN(1), IN_PROGRESS(2), COMPLETED(3), OTHER(4);

    private int num;

    ProgressEnum(int num) {
        this.num = num;
    }
}
