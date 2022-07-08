package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class T_NEW_RETAIL_DYGMVDTO implements Serializable {
    private Long id;

    @ApiModelProperty( name = "日期" )
    private LocalDate date;

    @ApiModelProperty( name = "帐号名称" )
    private String accountName;

    @ApiModelProperty( name = "帐号类型" )
    private String accountType;

    @ApiModelProperty( name = "合作模式" )
    private String coopMode;

    @ApiModelProperty( name = "成交金额" )
    private BigDecimal gmv;

    @ApiModelProperty( name = "成交人数" )
    private BigDecimal peopleNumber;

    @ApiModelProperty( name = "成交客单价" )
    private BigDecimal unitPrice;

    @ApiModelProperty( name = "商品点击成交率" )
    private BigDecimal clickTransactionRate;

    @ApiModelProperty( name = "直播成交金额" )
    private BigDecimal liveAmount;

    @ApiModelProperty( name = "短视频成交金额" )
    private BigDecimal shortVideoAmount;

    @ApiModelProperty( name = "商品卡成交金额" )
    private BigDecimal cardAmount;

    @ApiModelProperty( name = "商品卡成交金额" )
    private String storeName;

    @ApiModelProperty( name = "DY001" )
    private String dy001;

    @ApiModelProperty( name = "DY002" )
    private String dy002;

    @ApiModelProperty( name = "DY003" )
    private String dy003;

    @ApiModelProperty( name = "文件名" )
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCoopMode() {
        return coopMode;
    }

    public void setCoopMode(String coopMode) {
        this.coopMode = coopMode;
    }

    public BigDecimal getGmv() {
        return gmv;
    }

    public void setGmv(BigDecimal gmv) {
        this.gmv = gmv;
    }

    public BigDecimal getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(BigDecimal peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getClickTransactionRate() {
        return clickTransactionRate;
    }

    public void setClickTransactionRate(BigDecimal clickTransactionRate) {
        this.clickTransactionRate = clickTransactionRate;
    }

    public BigDecimal getLiveAmount() {
        return liveAmount;
    }

    public void setLiveAmount(BigDecimal liveAmount) {
        this.liveAmount = liveAmount;
    }

    public BigDecimal getShortVideoAmount() {
        return shortVideoAmount;
    }

    public void setShortVideoAmount(BigDecimal shortVideoAmount) {
        this.shortVideoAmount = shortVideoAmount;
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(BigDecimal cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getDy001() {
        return dy001;
    }

    public void setDy001(String dy001) {
        this.dy001 = dy001;
    }

    public String getDy002() {
        return dy002;
    }

    public void setDy002(String dy002) {
        this.dy002 = dy002;
    }

    public String getDy003() {
        return dy003;
    }

    public void setDy003(String dy003) {
        this.dy003 = dy003;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "T_NEW_RETAIL_DYGMVDTO{" +
                "id=" + id +
                ", date=" + date +
                ", accountName='" + accountName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", coopMode='" + coopMode + '\'' +
                ", gmv=" + gmv +
                ", peopleNumber=" + peopleNumber +
                ", unitPrice=" + unitPrice +
                ", clickTransactionRate=" + clickTransactionRate +
                ", liveAmount=" + liveAmount +
                ", shortVideoAmount=" + shortVideoAmount +
                ", cardAmount=" + cardAmount +
                ", storeName='" + storeName + '\'' +
                ", dy001='" + dy001 + '\'' +
                ", dy002='" + dy002 + '\'' +
                ", dy003='" + dy003 + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
