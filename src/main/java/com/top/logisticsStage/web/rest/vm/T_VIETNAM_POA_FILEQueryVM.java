package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class T_VIETNAM_POA_FILEQueryVM {

    @ApiModelProperty( value ="条码" )
    private String barCode;

    @ApiModelProperty( value ="品名" )
    private String itemName;

    @ApiModelProperty( value ="越南文" )
    private String vnString;

    @ApiModelProperty( value ="英文品名" )
    private String enString;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVnString() {
        return vnString;
    }

    public void setVnString(String vnString) {
        this.vnString = vnString;
    }

    public String getEnString() {
        return enString;
    }

    public void setEnString(String enString) {
        this.enString = enString;
    }
}
