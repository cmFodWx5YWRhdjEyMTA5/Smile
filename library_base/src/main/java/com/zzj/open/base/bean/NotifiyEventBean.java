package com.zzj.open.base.bean;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/8 20:56
 * @desc :
 * @version: 1.0
 */
public class NotifiyEventBean {

    public int type;
    public String msg = "";
    public String fromId;

    public NotifiyEventBean(int type, String fromId) {
        this.type = type;
        this.fromId = fromId;
    }
    public NotifiyEventBean(String msg, String fromId) {
        this.msg = msg;
        this.fromId = fromId;
    }

    public NotifiyEventBean(int type) {
        this.type = type;
    }
}
