package com.top.logisticsStage.domain.primarykey;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
public class T_MANUAL_FEE_XLS_SJPK implements Serializable {
    private String businessDepart;
    private String zone;
    private String area;
    private String systemFinance;
    private String customerFinance;
    private String system;
    private String finance;
    private String target1;
    private String target2;
    private String target3;
    private String yearMonth;
}
