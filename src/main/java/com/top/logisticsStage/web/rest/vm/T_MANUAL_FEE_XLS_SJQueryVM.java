package com.top.logisticsStage.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class T_MANUAL_FEE_XLS_SJQueryVM {

    @ApiModelProperty( name = "年月" )
    private String yearMonth;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
}
