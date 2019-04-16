package com.zzj.open.module_chat.bean;


import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.Expose;
import com.zzj.open.module_chat.utils.Cons;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zzj on 2018/5/10.
 */

@Entity
public class ChatMessageModel implements MultiItemEntity {

    //和服务器上的对应的消息类型常量
    public static final int CHAT_MSG_TYPE_TEXT = 1;
    public static final int CHAT_MSG_TYPE_PIC = 2;
    public static final int CHAT_MSG_TYPE_AUDIO = 3;

    //列表显示位置的判断常量
    public static final int CHAT_MSG_TYPE_LEFT_TEXT = 101;
    public static final int CHAT_MSG_TYPE_RIGHT_TEXT = 102;
    public static final int CHAT_MSG_TYPE_LEFT_PIC = 103;
    public static final int CHAT_MSG_TYPE_RIGHT_TPIC = 104;

    @Expose
    @Id
    private String msgId = UUID.randomUUID().toString();//消息id  随机生成一个UUID
    @Expose
    private String senderId;//发送者id
    @Expose
    private String receiverId;//接收者id
    @Expose
    private int type;//消息类型
    @Expose
    private int itemType;//消息item类型
    @Expose
    private String msg;//消息



    public ChatMessageModel() {

    }

    @Generated(hash = 1605742337)
    public ChatMessageModel(String msgId, String senderId, String receiverId, int type, int itemType, String msg) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.itemType = itemType;
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        if (senderId.equals(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID)) && type == CHAT_MSG_TYPE_TEXT) {
            itemType = CHAT_MSG_TYPE_RIGHT_TEXT;
        } else if (!senderId.equals(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID)) && type == CHAT_MSG_TYPE_TEXT) {
            itemType = CHAT_MSG_TYPE_LEFT_TEXT;
        } else if (senderId.equals(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID)) && type == CHAT_MSG_TYPE_PIC) {
            itemType = CHAT_MSG_TYPE_RIGHT_TPIC;
        } else if (!senderId.equals(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID)) && type == CHAT_MSG_TYPE_PIC) {
            itemType = CHAT_MSG_TYPE_LEFT_PIC;
        }
        return itemType;
    }





    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

}
