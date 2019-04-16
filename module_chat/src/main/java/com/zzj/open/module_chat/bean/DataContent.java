package com.zzj.open.module_chat.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 发送的消息实体类
 * @version: 1.0
 */
public class DataContent implements Serializable {

    private Integer action;//动作类型
    private ChatMessageModel chatMsg;//用户的聊天内容
    private String extand; //扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMessageModel getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMessageModel chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
