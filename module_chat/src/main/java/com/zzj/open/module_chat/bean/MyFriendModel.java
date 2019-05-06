package com.zzj.open.module_chat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 好友bean
 * @version: 1.0
 */
@Entity
public class MyFriendModel {

    @Id
    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;
    private boolean isSelect;
    @Generated(hash = 673771016)
    public MyFriendModel(String friendUserId, String friendUsername,
            String friendFaceImage, String friendNickname, boolean isSelect) {
        this.friendUserId = friendUserId;
        this.friendUsername = friendUsername;
        this.friendFaceImage = friendFaceImage;
        this.friendNickname = friendNickname;
        this.isSelect = isSelect;
    }

    @Generated(hash = 1164430959)
    public MyFriendModel() {
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendFaceImage() {
        return friendFaceImage;
    }

    public void setFriendFaceImage(String friendFaceImage) {
        this.friendFaceImage = friendFaceImage;
    }

    public String getFriendNickname() {
        return friendNickname;
    }

    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname;
    }

    public boolean getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
