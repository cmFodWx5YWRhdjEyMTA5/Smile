package com.zzj.open.module_chat.bean;

import java.io.Serializable;

/**
 * Created by zzj on 2018/2/7.
 */

public class User implements Serializable{


    /**
     * id : 3733c8fd-342a-4cee-a8fc-70afdec8ea3c
     * name : zzj
     * phone : null
     * portrait : null
     * desc : null
     * sex : 0
     * follows : 0
     * following : 0
     * isFollow : false
     * modifyAt : 2018-05-06T21:00:33.272
     */

    private String id;
    private String name;
    private String password;
    private String address;
    private String phone;
    private String portrait;
    private String desc;
    private int sex;
    private int follows;
    private int following;
    private boolean isFollow;
    private String modifyAt;
    /**
     * phone : null
     * coins : 0
     * money : 0
     */

    private String coins;
    private String money;
    //积分
    private String integral;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }


    public String getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(String modifyAt) {
        this.modifyAt = modifyAt;
    }


    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
