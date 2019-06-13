package com.zzj.open.module_lvji.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.w3c.dom.Text;

import java.util.Date;

public class LvjiPublishModel implements MultiItemEntity {

    public static final int TEXT = 1;

    public static final int IMG_TEXT = 2;

    private String id;

    private Date createAt;

    private Date updateAt;

    private Integer commentNum;

    private Integer likeNum;

    private String pictureUrlList;

    private String publishContent;

    private String userId;

    private String userName;

   private String faceImage;

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getPictureUrlList() {
        return pictureUrlList;
    }

    public void setPictureUrlList(String pictureUrlList) {
        this.pictureUrlList = pictureUrlList == null ? null : pictureUrlList.trim();
    }

    public String getPublishContent() {
        return publishContent;
    }

    public void setPublishContent(String publishContent) {
        this.publishContent = publishContent == null ? null : publishContent.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    @Override
    public int getItemType() {
        if(pictureUrlList.isEmpty()){
            return TEXT;
        }
        return IMG_TEXT;
    }
}