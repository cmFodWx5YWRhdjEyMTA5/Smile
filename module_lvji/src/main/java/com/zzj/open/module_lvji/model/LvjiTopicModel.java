package com.zzj.open.module_lvji.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/17
 * @desc :
 * @version: 1.0
 */
public class LvjiTopicModel implements Serializable {
    private String id;

    private Date createAt;

    private Date updateAt;

    private String topicContent;

    private String topicPicture;
    private String typeId;
    private String topicKind;
    private String topicTitle;

    private String userName;

    private String userId;

    private String topicLocation;

    public String getTopicLocation() {
        return topicLocation;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTopicKind() {
        return topicKind;
    }

    public void setTopicKind(String topicKind) {
        this.topicKind = topicKind;
    }

    public void setTopicLocation(String topicLocation) {
        this.topicLocation = topicLocation;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getTopicPicture() {
        return topicPicture;
    }

    public void setTopicPicture(String topicPicture) {
        this.topicPicture = topicPicture;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
