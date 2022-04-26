package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class T_MANUAL_NEWS_EC_LIST_STATEQueryVM {

    @ApiModelProperty( value ="料号" )
    private String itemCode;

    @ApiModelProperty( value ="料号简称" )
    private String itemName;

    @ApiModelProperty( value ="是否追踪" )
    private String onTrace;

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
}
