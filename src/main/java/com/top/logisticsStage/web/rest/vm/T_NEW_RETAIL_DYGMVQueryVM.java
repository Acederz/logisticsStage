package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class T_NEW_RETAIL_DYGMVQueryVM {

    @ApiModelProperty( name = "开始日期" )
    private LocalDate startDate;

    @ApiModelProperty( name = "结束日期" )
    private LocalDate endDate;

    @ApiModelProperty( name = "账号名称" )
    private String accountName;

    @ApiModelProperty( name = "账号类型" )
    private String accountType;

    @ApiModelProperty( name = "合作模式" )
    private String coopMode;

    @ApiModelProperty( name = "店铺名" )
    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
}
