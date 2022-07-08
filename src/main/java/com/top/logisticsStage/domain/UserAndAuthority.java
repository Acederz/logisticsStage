package com.top.logisticsStage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.io.Serializable;

@ApiModel(description = "用户权限管理")
@Entity
@Table(name = "T_USER_AUTHORITY")
public class UserAndAuthority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "ID" )
    private Long id;


    @ManyToOne
    private User user;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    @ManyToOne
    private Authority authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
