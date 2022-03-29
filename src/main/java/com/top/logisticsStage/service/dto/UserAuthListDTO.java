package com.top.logisticsStage.service.dto;

import java.util.ArrayList;
import java.util.List;

public class UserAuthListDTO {

    private Long id;

    private String title;

    private String path;

    private List<UserAuthListDTO> child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<UserAuthListDTO> getChild() {
        return child;
    }

    public void setChild(List<UserAuthListDTO> child) {
        this.child = child;
    }
}
