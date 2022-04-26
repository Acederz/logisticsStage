package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class T_LAZADA_COSTQueryVM {

    @ApiModelProperty( value = "CT001" )
    private String ct001;

    @ApiModelProperty( value = "CT002" )
    private String ct002;

    public String getCt001() {
        return ct001;
    }

    public void setCt001(String ct001) {
        this.ct001 = ct001;
    }

    public String getCt002() {
        return ct002;
    }

    public void setCt002(String ct002) {
        this.ct002 = ct002;
    }
}
