package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class T_JN_POSDTO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "年")
    private BigDecimal year;

    @ApiModelProperty(value = "月")
    private BigDecimal month;

    @ApiModelProperty(value = "系统名")
    private String sysName;

    @ApiModelProperty(value = "系统码")
    private String sysCode;

    @ApiModelProperty(value = "门店门")
    private String storeName;

    @ApiModelProperty(value = "商品编号")
    private String itemCode;

    @ApiModelProperty(value = "商品名")
    private String itemName;

    @ApiModelProperty(value = "商品条码")
    private String barCode;

    @ApiModelProperty(value = "销售量")
    private BigDecimal saleQuantity;

    @ApiModelProperty(value = "销售额")
    private BigDecimal saleAmount;

    @ApiModelProperty(value = "销售日期")
    private LocalDate saleDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(BigDecimal saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
