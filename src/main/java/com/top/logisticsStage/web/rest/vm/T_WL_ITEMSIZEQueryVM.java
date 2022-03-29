package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class T_WL_ITEMSIZEQueryVM {

    @ApiModelProperty(value = "条码")
    private String barCode;

    @ApiModelProperty(value = "货品编号" )
    private String itemCode;

    @ApiModelProperty(value = "品名" )
    private String productName;

    @ApiModelProperty(value = "更新时间")
    private LocalDate updateTimeStart;

    @ApiModelProperty(value = "更新时间")
    private LocalDate updateTimeEnd;

    @ApiModelProperty(value = "上架时间")
    private LocalDate launchTimeStart;

    @ApiModelProperty(value = "上架时间")
    private LocalDate launchTimeEnd;

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

    public LocalDate getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(LocalDate updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public LocalDate getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(LocalDate updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public LocalDate getLaunchTimeStart() {
        return launchTimeStart;
    }

    public void setLaunchTimeStart(LocalDate launchTimeStart) {
        this.launchTimeStart = launchTimeStart;
    }

    public LocalDate getLaunchTimeEnd() {
        return launchTimeEnd;
    }

    public void setLaunchTimeEnd(LocalDate launchTimeEnd) {
        this.launchTimeEnd = launchTimeEnd;
    }
}
