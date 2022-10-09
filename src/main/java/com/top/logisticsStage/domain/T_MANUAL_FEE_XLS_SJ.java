package com.top.logisticsStage.domain;

import com.top.logisticsStage.domain.enumeration.TargetType;
import com.top.logisticsStage.domain.primarykey.T_MANUAL_FEE_XLS_SJPK;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "新零售费用实际")
@Data
@Entity
@Table( name = "T_MANUAL_FEE_XLS_SJ" )
@IdClass(value = T_MANUAL_FEE_XLS_SJPK.class)
public class T_MANUAL_FEE_XLS_SJ {

    @Id
    @Column( name = "事业部" )
    private String businessDepart;
    @Id
    @Column( name = "战区" )
    private String zone;
    @Id
    @Column( name = "片区" )
    private String area;
    @Id
    @Column( name = "系统_财务" )
    private String systemFinance;
    @Id
    @Column( name = "客户名称_财务" )
    private String customerFinance;
    @Id
    @Column( name = "系统" )
    private String system;
    @Id
    @Column( name = "财务" )
    private String finance;
    @Id
    @Column( name = "统计指标1" )
    private String target1;
    @Id
    @Column( name = "统计指标2" )
    private String target2;
    @Id
    @Column( name = "统计指标3" )
    private String target3;
    @Id
    @Column( name = "年月" )
    private String yearMonth;


}
