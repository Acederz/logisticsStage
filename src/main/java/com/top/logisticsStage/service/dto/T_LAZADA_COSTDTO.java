package com.top.logisticsStage.service.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class T_LAZADA_COSTDTO implements Serializable {

    @ApiModelProperty( value = "CT001" )
    private String ct001;

    @ApiModelProperty( value = "CT002" )
    private String ct002;

    @ApiModelProperty( value = "CT003" )
    private String ct003;

    @ApiModelProperty( value = "CT004" )
    private String ct004;

    @ApiModelProperty( value = "CT005" )
    private String ct005;

    @ApiModelProperty( value = "CT006" )
    private String ct006;

    public String getCt001() {
        return ct001;
    }

    public void setCt001(String ct001) {
        this.ct001 = ct001;
    }

    public String getCt002() {
        return ct002;
    }

    public void setCt002(String ct002) {
        this.ct002 = ct002;
    }

    public String getCt003() {
        return ct003;
    }

    public void setCt003(String ct003) {
        this.ct003 = ct003;
    }

    public String getCt004() {
        return ct004;
    }

    public void setCt004(String ct004) {
        this.ct004 = ct004;
    }

    public String getCt005() {
        return ct005;
    }

    public void setCt005(String ct005) {
        this.ct005 = ct005;
    }

    public String getCt006() {
        return ct006;
    }

    public void setCt006(String ct006) {
        this.ct006 = ct006;
    }

    @Override
    public String toString() {
        return "T_LAZADA_COSTDTO{" +
                "ct001='" + ct001 + '\'' +
                ", ct002='" + ct002 + '\'' +
                ", ct003='" + ct003 + '\'' +
                ", ct004='" + ct004 + '\'' +
                ", ct005='" + ct005 + '\'' +
                ", ct006='" + ct006 + '\'' +
                '}';
    }
}
