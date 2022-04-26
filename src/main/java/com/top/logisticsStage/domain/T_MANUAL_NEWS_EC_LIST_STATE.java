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

    @Override
    public String toString() {
        return "T_MANUAL_NEWS_EC_LIST_STATEMapper{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", onTrace=" + onTrace +
                '}';
    }
}
