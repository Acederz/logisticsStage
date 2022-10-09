package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(description = "分公司客户追踪")
@Entity
@Table( name = "T_MBI_CUSTOMER_TARGET" )
public class T_MBI_CUSTOMER_TARGET implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @Column( name = "战区")
    private String warZone;

    @Column( name = "客户")
    private String customer;

    @Column( name = "年", precision = 4, scale = 0)
    private BigDecimal year;

    @Column( name = "月", precision = 2, scale = 0)
    private BigDecimal month;

    @Column( name = "目标", precision = 19, scale = 5)
    private BigDecimal target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarZone() {
        return warZone;
    }

    public void setWarZone(String warZone) {
        this.warZone = warZone;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "T_MBI_CUSTOMER_TARGET{" +
                "id=" + id +
                ", warZone='" + warZone + '\'' +
                ", customer='" + customer + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", target=" + target +
                '}';
    }
}
