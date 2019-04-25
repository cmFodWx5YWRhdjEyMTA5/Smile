package com.zzj.open.module_chat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc :  消息列表的实体类
 * @version: 1.0
 */
@Entity
public class ChatListModel {

    /**
     * 聊天对象的头像
     */
    private String chatFaceImage;
    /**
     * 聊天对象
     */
    private String chatUserName;
    /**
     * 消息
     */
    private String msg;
    /**
     * 时间
     */
    private String time;
    /**
     * 聊天用户id
     */
    @Id
    private String chatUserId;

    private boolean isSend;
    /**
     * 未读数量
     */
    private int unreadNum;

    @Generated(hash = 1860871288)
    public ChatListModel(String chatFaceImage, String chatUserName, String msg,
            String time, String chatUserId, boolean isSend, int unreadNum) {
        this.chatFaceImage = chatFaceImage;
        this.chatUserName = chatUserName;
        this.msg = msg;
        this.time = time;
        this.chatUserId = chatUserId;
        this.isSend = isSend;
        this.unreadNum = unreadNum;
    }

    @Generated(hash = 2047655589)
    public ChatListModel() {
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getChatFaceImage() {
        return chatFaceImage;
    }

    public void setChatFaceImage(String chatFaceImage) {
        this.chatFaceImage = chatFaceImage;
    }

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public boolean getIsSend() {
        return this.isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }
}
