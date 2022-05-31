package com.top.logisticsStage.domain;

import com.top.logisticsStage.domain.enumeration.TargetType;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "目标导入")
@Entity
@Table( name = "T_MANUAL_EST_EC" )
public class T_MANUAL_EST_EC implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @Column( name = "料号" )
    private String itemCode;

    @Column( name = "年", precision = 4, scale = 0)
    private BigDecimal year;

    @Column( name = "月", precision = 2, scale = 0)
    private BigDecimal month;

    @Enumerated(EnumType.STRING)
    @Column(name = "目标类型")
    private TargetType targetType;

    @Column( name = "目标零支销量", precision = 10, scale = 2)
    private BigDecimal saleNumber;

    @Column( name = "目标零支销售单价", precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column( name = "目标财务毛利额", precision = 10, scale = 2)
    private BigDecimal saleAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public BigDecimal getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(BigDecimal saleNumber) {
        this.saleNumber = saleNumber;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    @Override
    public String toString() {
        return "T_MANUAL_EST_EC{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", targetType=" + targetType +
                ", saleNumber=" + saleNumber +
                ", salePrice=" + salePrice +
                ", saleAmount=" + saleAmount +
                '}';
    }
}
