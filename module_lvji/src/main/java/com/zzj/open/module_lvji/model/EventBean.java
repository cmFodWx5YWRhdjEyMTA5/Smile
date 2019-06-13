package com.zzj.open.module_lvji.model;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/12 20:40
 * @desc : eventBus消息bean
 * @version: 1.0
 */
public class EventBean implements Serializable {

    private String msg;
    private int code;

    public EventBean(int code) {
        this.code = code;
    }

    public EventBean(String msg) {
        this.msg = msg;
    }

    public EventBean(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
