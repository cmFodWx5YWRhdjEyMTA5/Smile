package com.zzj.open.module_chat.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 好友bean
 * @version: 1.0
 */
public class MyFriendBean implements Serializable {

    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

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
}
