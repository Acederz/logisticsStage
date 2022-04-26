package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class T_MANUAL_NEWS_EC_LIST_STATEDTO implements Serializable {

    @ApiModelProperty( value ="料号" )
    private String itemCode;

    @ApiModelProperty( value ="料号简称" )
    private String itemName;

    @ApiModelProperty( value ="是否追踪" )
    private String onTrace;

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

    @Override
    public String toString() {
        return "T_MANUAL_NEWS_EC_LIST_STATEDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", onTrace='" + onTrace + '\'' +
                ", oldItemCode='" + oldItemCode + '\'' +
                '}';
    }
}
