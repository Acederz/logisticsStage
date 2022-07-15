package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel(description = "新品目标")
@Entity
@Table( name = "T_MANUAL_NEWS_EC_LIST_STATE" )
public class T_MANUAL_NEWS_EC_LIST_STATE implements Serializable {

    @Id
    @Column( name = "料号" )
    private String itemCode;

    @Column( name = "料号简称" )
    private String itemName;

    @Column( name = "是否追踪" )
    private String onTrace;

    @Column( name = "负责人" )
    private String charge;

    @Column( name = "系列" )
    private String series;

    @Column( name = "事业部" )
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

    @Override
    public String toString() {
        return "T_MANUAL_NEWS_EC_LIST_STATE{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", onTrace='" + onTrace + '\'' +
                ", charge='" + charge + '\'' +
                ", series='" + series + '\'' +
                ", division='" + division + '\'' +
                '}';
    }
}
