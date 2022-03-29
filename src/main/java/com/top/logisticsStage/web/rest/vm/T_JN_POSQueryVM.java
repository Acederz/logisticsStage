package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class T_JN_POSQueryVM {

    @ApiModelProperty(value = "年")
    private BigDecimal year;

    @ApiModelProperty(value = "月")
    private BigDecimal month;

    @ApiModelProperty(value = "系统码")
    private String sysCode;

    @ApiModelProperty(value = "门店名")
    private String storeName;

    @ApiModelProperty(value = "商品名")
    private String itemName;

    @ApiModelProperty(value = "商品条码")
    private String barCode;

    @ApiModelProperty(value = "销售日期")
    private LocalDate saleDateStart;

    @ApiModelProperty(value = "销售日期")
    private LocalDate saleDateEnd;

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

    public LocalDate getSaleDateStart() {
        return saleDateStart;
    }

    public void setSaleDateStart(LocalDate saleDateStart) {
        this.saleDateStart = saleDateStart;
    }

    public LocalDate getSaleDateEnd() {
        return saleDateEnd;
    }

    public void setSaleDateEnd(LocalDate saleDateEnd) {
        this.saleDateEnd = saleDateEnd;
    }
}
