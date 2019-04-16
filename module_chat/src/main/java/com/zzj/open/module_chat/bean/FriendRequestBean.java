package com.zzj.open.module_chat.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc :
 * @version: 1.0
 */
public class FriendRequestBean implements Serializable {

    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    public String getSendFaceImage() {
        return sendFaceImage;
    }

    public void setSendFaceImage(String sendFaceImage) {
        this.sendFaceImage = sendFaceImage;
    }

    public String getSendNickname() {
        return sendNickname;
    }

    public void setSendNickname(String sendNickname) {
        this.sendNickname = sendNickname;
    }
}
