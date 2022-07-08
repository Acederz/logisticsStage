package com.top.logisticsStage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(description = "抖音电商轮盘价格分析")
@Entity
@Table( name = "T_NEW_RETAIL_DYGMV" )
public class T_NEW_RETAIL_DYGMV implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column( name = "ID" )
    private Long id;

    @Column( name = "日期" )
    private LocalDate date;

    @Column( name = "账号名称" )
    private String accountName;

    @Column( name = "账号类型" )
    private String accountType;

    @Column( name = "合作模式" )
    private String coopMode;

    @Column( name = "成交金额", precision = 16, scale = 2 )
    private BigDecimal gmv;

    @Column( name = "成交人数", precision = 16, scale = 0 )
    private BigDecimal peopleNumber;

    @Column( name = "成交客单价", precision = 16, scale = 2 )
    private BigDecimal unitPrice;

    @Column( name = "商品点击成交率", precision = 16, scale = 2 )
    private BigDecimal clickTransactionRate;

    @Column( name = "直播成交金额", precision = 16, scale = 2 )
    private BigDecimal liveAmount;

    @Column( name = "短视频成交金额", precision = 16, scale = 2 )
    private BigDecimal shortVideoAmount;

    @Column( name = "商品卡成交金额", precision = 16, scale = 2 )
    private BigDecimal cardAmount;

    @Column( name = "店铺名" )
    private String storeName;

    @Column( name = "DY001" )
    private String dy001;

    @Column( name = "DY002" )
    private String dy002;

    @Column( name = "DY003" )
    private String dy003;

    @Column( name = "文件名" )
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
        return "T_NEW_RETAIL_DYGMV{" +
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
