package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class T_MANUAL_NEWS_EC_LIST_STATEQueryVM {

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
}
