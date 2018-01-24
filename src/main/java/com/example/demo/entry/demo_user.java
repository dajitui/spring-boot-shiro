package com.example.demo.entry;

import java.io.Serializable;

public class demo_user implements Serializable {
    private Integer id;

    private String name;

    private String pws;

    private String roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPws() {
        return pws;
    }

    public void setPws(String pws) {
        this.pws = pws == null ? null : pws.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}