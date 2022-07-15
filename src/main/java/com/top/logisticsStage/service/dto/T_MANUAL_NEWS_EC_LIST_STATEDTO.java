package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;

public class T_MANUAL_NEWS_EC_LIST_STATEDTO implements Serializable {

    @ApiModelProperty( value ="料号" )
    private String itemCode;

    @ApiModelProperty( value ="料号简称" )
    private String itemName;

    @ApiModelProperty( value ="是否追踪" )
    private String onTrace;

    @ApiModelProperty( value = "负责人" )
    private String charge;

    @ApiModelProperty( value = "系列" )
    private String series;

    @ApiModelProperty( value = "事业部" )
    private String division;

    @ApiModelProperty( value ="旧料号" )
    private String oldItemCode;

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

    public String getOnTrace() {
        return onTrace;
    }

    public void setOnTrace(String onTrace) {
        this.onTrace = onTrace;
    }

    public String getOldItemCode() {
        return oldItemCode;
    }

    public void setOldItemCode(String oldItemCode) {
        this.oldItemCode = oldItemCode;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "T_MANUAL_NEWS_EC_LIST_STATEDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", onTrace='" + onTrace + '\'' +
                ", charge='" + charge + '\'' +
                ", series='" + series + '\'' +
                ", division='" + division + '\'' +
                ", oldItemCode='" + oldItemCode + '\'' +
                '}';
    }
}
