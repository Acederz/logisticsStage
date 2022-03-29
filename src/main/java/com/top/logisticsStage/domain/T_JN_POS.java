package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(description = "济南分POS表")
@Entity
@Table( name = "T_JN_POS" )
public class T_JN_POS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @Column( name = "YEAR", precision = 4, scale = 0)
    private BigDecimal year;

    @Column( name = "MONTH", precision = 2, scale = 0)
    private BigDecimal month;

    @Column( name = "SYSNAME")
    private String sysName;

    @Column( name = "SYSCODE")
    private String sysCode;

    @Column( name = "STORENAME")
    private String storeName;

    @Column( name = "ITEMCODE")
    private String itemCode;

    @Column( name = "ITEMNAME")
    private String itemName;

    @Column( name = "BARCODE")
    private String barCode;

    @Column( name = "SALEQUANTITY", precision = 16, scale = 4)
    private BigDecimal saleQuantity;

    @Column( name = "SALEAMOUNT", precision = 16, scale = 4)
    private BigDecimal saleAmount;

    @Column( name = "SALEDATE")
    private LocalDate saleDate;

    @Column( name = "UPDATETIME")
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
