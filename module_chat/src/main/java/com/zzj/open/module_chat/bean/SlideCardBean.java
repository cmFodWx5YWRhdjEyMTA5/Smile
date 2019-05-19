package com.zzj.open.module_chat.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 11:15
 * @desc : 卡片滑动bean
 * @version: 1.0
 */
public class SlideCardBean implements Serializable{
    //昵称
    private String nickName;
    //背景图片
    private String picture;
    //职业
    private String profession;
    //个性签名
    private String description;

    //星座
    private String constellation;
    //图片数量
    private int pictureNum;
    //年龄
    private int age;
    //未知参数
    private int unKnow;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public int getPictureNum() {
        return pictureNum;
    }

    public void setPictureNum(int pictureNum) {
        this.pictureNum = pictureNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUnKnow() {
        return unKnow;
    }

    public void setUnKnow(int unKnow) {
        this.unKnow = unKnow;
    }
}
