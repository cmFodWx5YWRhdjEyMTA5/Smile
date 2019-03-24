package com.zzj.open.base.bean;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * APp更新实体类
 */
public class UpdateVersion implements Serializable{

    @Expose
    private String id;

    /**
     * 升级版本号
     */

    @Expose
    private int versionCode;

    /**
     * 版本名称
     */

    @Expose
    private String versionName;

    /**
     * 版本描述
     */


    @Expose
    private String versionDesc;

    /**
     * 下载地址
     */

    @Expose
    private String downLoadUrl;



    private String fileMD5;

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
