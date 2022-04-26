package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel(description = "Lazada费用说明对照表")
@Entity
@Table( name = "T_LAZADA_COST" )
public class T_LAZADA_COST implements Serializable {

    @Id
    @Column( name = "CT001" )
    private String ct001;

    @Column( name = "CT002" )
    private String ct002;

    @Column( name = "CT003" )
    private String ct003;

    @Column( name = "CT004" )
    private String ct004;

    @Column( name = "CT005" )
    private String ct005;

    @Column( name = "CT006" )
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
        return "T_LAZADA_COST{" +
                "ct001='" + ct001 + '\'' +
                ", ct002='" + ct002 + '\'' +
                ", ct003='" + ct003 + '\'' +
                ", ct004='" + ct004 + '\'' +
                ", ct005='" + ct005 + '\'' +
                ", ct006='" + ct006 + '\'' +
                '}';
    }
}
