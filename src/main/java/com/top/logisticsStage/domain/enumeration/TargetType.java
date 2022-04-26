package com.top.logisticsStage.domain.enumeration;

/**
 * T_MANUAL_EST_EC
 * 目标类型
 */
public enum TargetType {
    M("月"),
    Y("年");

    private final String description;

    private TargetType(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
