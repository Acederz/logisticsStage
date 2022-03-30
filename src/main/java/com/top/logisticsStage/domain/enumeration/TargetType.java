package com.top.logisticsStage.domain.enumeration;

/**
 * T_MANUAL_EST_EC
 * 目标类型
 */
public enum TargetType {
    M("年"),
    Y("月");

    private final String description;

    private TargetType(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
