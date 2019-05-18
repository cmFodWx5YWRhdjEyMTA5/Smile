package com.zzj.open.module_chat.bean;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 群信息Model
 *
 * @author qiujuer Email:qiujuer.live.cn
 */
@Entity
public class GroupCard {
    @Id
    private String id;// Id
    private String name;// 名称
    private String desc;// 描述
    private String picture;// 群图片
    private String ownerId;// 创建者Id
    private int notifyLevel;// 对于当前用户的通知级别
    private Date joinAt;// 加入时间
    private Date modifyAt;// 最后修改时间

    @Generated(hash = 1656647812)
    public GroupCard(String id, String name, String desc, String picture,
            String ownerId, int notifyLevel, Date joinAt, Date modifyAt) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.picture = picture;
        this.ownerId = ownerId;
        this.notifyLevel = notifyLevel;
        this.joinAt = joinAt;
        this.modifyAt = modifyAt;
    }

    @Generated(hash = 111873520)
    public GroupCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return picture == null ? "" : picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
}
