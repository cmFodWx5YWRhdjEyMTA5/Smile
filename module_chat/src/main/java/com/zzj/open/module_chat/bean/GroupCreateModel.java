package com.zzj.open.module_chat.bean;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

/**
 * 群添加的Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class GroupCreateModel {
    //创建者
    @Expose
    private String userid;
    @Expose
    private String name;
    @Expose
    private String desc;
    @Expose
    private String picture;
    @Expose
    private Set<String> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
