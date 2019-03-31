package com.zzj.open.module_chat.bean;

import android.net.Uri;

public class ImageInfo {

    private final Uri mUri ;
    private int mWidth;
    private int mHeight;
    private boolean mNeedResize;
    private String thumbnail_path;
    public ImageInfo(Uri mUri, int mWidth, int mHeight) {
        this.mUri = mUri;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public Uri getmUri() {
        return mUri;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public boolean ismNeedResize() {
        return mNeedResize;
    }

    public void setmNeedResize(boolean mNeedResize) {
        this.mNeedResize = mNeedResize;
    }
}
