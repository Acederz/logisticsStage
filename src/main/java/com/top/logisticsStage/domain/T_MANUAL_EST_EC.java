package com.top.logisticsStage.domain;

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

    @Column( name = "YEAR", precision = 4, scale = 0)
    private BigDecimal year;

    @Column( name = "MONTH", precision = 2, scale = 0)
    private BigDecimal month;

    @Column( name = "品名" )
    private String productName;

    @Column( name = "长", precision = 10, scale = 2)
    private BigDecimal length;

    @Column( name = "宽", precision = 10, scale = 2)
    private BigDecimal width;

    @Column( name = "高", precision = 10, scale = 2)
    private BigDecimal height;

    @Column( name = "更新时间")
    private LocalDate updateTime;

    @Column( name = "上架日期")
    private LocalDate launchTime;
}
