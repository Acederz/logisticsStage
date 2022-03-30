package com.top.logisticsStage.service.dto;

import com.top.logisticsStage.domain.enumeration.TargetType;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;

public class T_MANUAL_EST_ECDTO implements Serializable {

    @ApiModelProperty( value = "ID" )
    private Long id;

    @ApiModelProperty( value = "料号" )
    private String itemCode;

    @ApiModelProperty( value = "年")
    private BigDecimal year;

    @ApiModelProperty( value = "月")
    private BigDecimal month;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "目标类型")
    private TargetType targetType;

    @ApiModelProperty( value = "目标零支销售量")
    private BigDecimal saleNumber;

    @ApiModelProperty( value = "目标零支销售单价")
    private BigDecimal salePrice;

    @ApiModelProperty( value = "目标财务毛利额")
    private BigDecimal saleAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(BigDecimal saleNumber) {
        this.saleNumber = saleNumber;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    @Override
    public String toString() {
        return "T_MANUAL_EST_ECDTO{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", targetType=" + targetType +
                ", saleNumber=" + saleNumber +
                ", salePrice=" + salePrice +
                ", saleAmount=" + saleAmount +
                '}';
    }
}
