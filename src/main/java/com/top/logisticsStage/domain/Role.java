package com.top.logisticsStage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "角色管理")
@Entity
@Table( name = "T_ROLE" )
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;

    @ApiModelProperty(value = "角色")
    @Column( name = "ROLE" )
    private String role;

    @ApiModelProperty(value = "角色描述")
    @Column( name = "DESCRIBE" )
    private String describe;

    /**
     * 角色和权限
     */
    @ApiModelProperty(value = "角色和权限")
    @ManyToMany
    @JoinTable(name = "T_ROLE_AUTHORITY",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Role() {
    }

    public Role(String role, String describe) {
        this.role = role;
        this.describe = describe;
    }
}
