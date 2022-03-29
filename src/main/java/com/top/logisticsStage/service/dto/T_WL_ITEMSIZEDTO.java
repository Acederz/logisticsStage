package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class T_WL_ITEMSIZEDTO implements Serializable {

    @ApiModelProperty(value = "条码")
    private String barCode;

    @ApiModelProperty(value = "货品编号" )
    private String itemCode;

    @ApiModelProperty(value = "品名" )
    private String productName;

    @ApiModelProperty(value = "长")
    private BigDecimal length;

    @ApiModelProperty(value = "宽")
    private BigDecimal width;

    @ApiModelProperty(value = "高")
    private BigDecimal height;

    @ApiModelProperty(value = "更新时间")
    private LocalDate updateTime;

    @ApiModelProperty(value = "上架时间")
    private LocalDate launchTime;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDate getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(LocalDate launchTime) {
        this.launchTime = launchTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "T_WL_ITEMSIZEDTO{" +
                "barCode='" + barCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", productName='" + productName + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", updateTime=" + updateTime +
                ", launchTime=" + launchTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
