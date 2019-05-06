package com.zzj.open.module_chat.bean;

import com.google.gson.annotations.Expose;
import com.zzj.open.base.bean.UsersVO;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 群成员Model
 * @author qiujuer Email:qiujuer.live.cn
 */
public class GroupMemberCard {
    @Expose
    private String id;// Id
    @Expose
    private String alias;// 别名／备注
    @Expose
    private boolean isAdmin;// 是否是管理员
    @Expose
    private boolean isOwner;// 是否是创建者
    @Expose
    private UsersVO users;
    @Expose
    private String userId;// 对于的用户Id
    @Expose
    private String groupId;// 对于的群Id
    @Expose
    private Date modifyAt;// 最后修改时间

    public UsersVO getUsers() {
        return users;
    }

    public void setUsers(UsersVO users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
}
