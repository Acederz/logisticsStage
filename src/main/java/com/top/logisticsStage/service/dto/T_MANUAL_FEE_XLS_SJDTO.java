package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class T_MANUAL_FEE_XLS_SJDTO implements Serializable {
    
    @ApiModelProperty(value = "ID" )
    private Long id;

    @ApiModelProperty(value = "战区" )
    private String zone;

    @ApiModelProperty(value = "片区" )
    private String area;

    @ApiModelProperty(value = "事业部" )
    private String businessDepart;

    @ApiModelProperty(value = "所属期间" )
    private String period;

    @ApiModelProperty(value = "预提" )
    private String withholding;

    @ApiModelProperty(value = "系统_财务" )
    private String systemFinance;

    @ApiModelProperty(value = "客户名称_财务" )
    private String customerFinance;

    @ApiModelProperty(value = "系统" )
    private String system;

    @ApiModelProperty(value = "客户名称" )
    private String customer;

    @ApiModelProperty(value = "统计指标1" )
    private String target1;

    @ApiModelProperty(value = "统计指标2" )
    private String target2;

    @ApiModelProperty(value = "统计指标3" )
    private String target3;

    @ApiModelProperty(value = "年月" )
    private String yearMonth;

    @ApiModelProperty(value = "实际" )
    private BigDecimal actual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBusinessDepart() {
        return businessDepart;
    }

    public void setBusinessDepart(String businessDepart) {
        this.businessDepart = businessDepart;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getWithholding() {
        return withholding;
    }

    public void setWithholding(String withholding) {
        this.withholding = withholding;
    }

    public String getSystemFinance() {
        return systemFinance;
    }

    public void setSystemFinance(String systemFinance) {
        this.systemFinance = systemFinance;
    }

    public String getCustomerFinance() {
        return customerFinance;
    }

    public void setCustomerFinance(String customerFinance) {
        this.customerFinance = customerFinance;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTarget1() {
        return target1;
    }

    public void setTarget1(String target1) {
        this.target1 = target1;
    }

    public String getTarget2() {
        return target2;
    }

    public void setTarget2(String target2) {
        this.target2 = target2;
    }

    public String getTarget3() {
        return target3;
    }

    public void setTarget3(String target3) {
        this.target3 = target3;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public BigDecimal getActual() {
        return actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }
}
