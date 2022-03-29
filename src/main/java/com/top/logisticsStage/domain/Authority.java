package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "权限管理")
@Entity
@Table( name = "T_AUTHORITY" )
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @ApiModelProperty(value = "权限")
    @Column( name = "AUTHORITY" )
    private String authority;

    @ApiModelProperty(value = "关联url")
    @Column( name = "URL" )
    private String url;

    @ApiModelProperty(value = "权限描述")
    @Column( name = "DESCRIBE" )
    private String describe;

    @ManyToOne
    private Authority parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Authority getParent() {
        return parent;
    }

    public void setParent(Authority parent) {
        this.parent = parent;
    }

    public Authority() {
    }

    public Authority(Long id, String authority, String url, String describe, Authority parent) {
        this.id = id;
        this.authority = authority;
        this.url = url;
        this.describe = describe;
        this.parent = parent;
    }

    public Authority(String authority, String url, String describe) {
        this.authority = authority;
        this.url = url;
        this.describe = describe;
    }
}
