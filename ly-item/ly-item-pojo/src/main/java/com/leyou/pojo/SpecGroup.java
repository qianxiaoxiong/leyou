package com.leyou.pojo;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class SpecGroup implements Serializable {
    private Long id;

    private Long cid;

    private String name;


    //改造
    @Transient
    private List<SpecParam> params;

    private static final long serialVersionUID = 1L;


    public List<SpecParam> getParams() {
        return params;
    }

    public void setParams(List<SpecParam> params) {
        this.params = params;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}