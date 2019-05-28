package com.zzj.open.base.bean;

public class UsersVO {


    private String id;

    private String username;

    private String password;
    private String faceImage;

    private String faceImageBig;

    private String nickname;

    private String qrcode;

    private String description;

    private Integer chatSex;

    private String professional;

    private Integer bswAge;

    private Integer pictureNum;

    private String bswConstellation;

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public Integer getBswAge() {
        return bswAge;
    }

    public void setBswAge(Integer bswAge) {
        this.bswAge = bswAge;
    }

    public Integer getPictureNum() {
        return pictureNum;
    }

    public void setPictureNum(Integer pictureNum) {
        this.pictureNum = pictureNum;
    }

    public String getBswConstellation() {
        return bswConstellation;
    }

    public void setBswConstellation(String bswConstellation) {
        this.bswConstellation = bswConstellation;
    }

    public Integer getChatSex() {
        return chatSex;
    }

    public void setChatSex(Integer chatSex) {
        this.chatSex = chatSex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getFaceImageBig() {
        return faceImageBig;
    }

    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}