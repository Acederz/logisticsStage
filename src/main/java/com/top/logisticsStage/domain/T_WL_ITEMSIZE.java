package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "物流商品尺寸表")
@Entity
@Table( name = "T_WL_ITEMSIZE" )
public class T_WL_ITEMSIZE implements Serializable {

    @Id
    @Column( name = "条码" )
    private String barCode;

    @Column( name = "货品编号" )
    private String itemCode;

    @Column( name = "品名" )
    private String productName;

    @Column( name = "长", precision = 10, scale = 2)
    private BigDecimal length;

    @Column( name = "宽", precision = 10, scale = 2)
    private BigDecimal width;

    @Column( name = "高", precision = 10, scale = 2)
    private BigDecimal height;

    @Column( name = "更新时间")
    private LocalDate updateTime;

    @Column( name = "上架日期")
    private LocalDate launchTime;

    @Column( name = "备注")
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
        return "T_WL_ITEMSIZE{" +
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
