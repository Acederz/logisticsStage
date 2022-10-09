package com.top.logisticsStage.web.rest.vm;

import com.top.logisticsStage.domain.enumeration.TargetType;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class T_MANUAL_EST_ECQueryVM {

    @ApiModelProperty(value = "料号")
    private String itemCode;

    @ApiModelProperty(value = "年" )
    private BigDecimal year;

    @ApiModelProperty(value = "月" )
    private BigDecimal month;

    @ApiModelProperty(value = "目标类型")
    private TargetType targetType;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "T_MANUAL_EST_ECQueryVM{" +
                "itemCode='" + itemCode + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", targetType=" + targetType +
                '}';
    }
}
