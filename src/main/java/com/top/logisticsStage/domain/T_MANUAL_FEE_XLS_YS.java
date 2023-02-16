package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "新零售费用预算")
@Data
@Entity
@Table( name = "T_MANUAL_FEE_XLS_YS" )
public class T_MANUAL_FEE_XLS_YS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @Column( name = "事业部" )
    private String businessDepart;

    @Column( name = "战区" )
    private String zone;

    @Column( name = "片区" )
    private String area;

    @Column( name = "系统_财务" )
    private String systemFinance;

    @Column( name = "客户名称_财务" )
    private String customerFinance;

    @Column( name = "系统" )
    private String system;

    @Column( name = "客户名称" )
    private String customer;

    @Column( name = "统计指标1" )
    private String target1;

    @Column( name = "统计指标2" )
    private String target2;

    @Column( name = "统计指标3" )
    private String target3;

    @Column( name = "年月" )
    private String yearMonth;

    @Column( name = "预算", precision = 16, scale = 4)
    private BigDecimal budget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessDepart() {
        return businessDepart;
    }

    public void setBusinessDepart(String businessDepart) {
        this.businessDepart = businessDepart;
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

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
