package com.zzj.open.module_movie.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/14 12:31
 * @desc :
 * @version: 1.0
 */
public class MovieBean implements Serializable{


    /**
     * id : 5750812a-3b55-484a-bcab-f5d76b542fbf
     * title : 报告老师我是东北银
     * img : http://ww3.sinaimg.cn/large/87c01ec7gy1fpxantxo9fj20780a40tm.jpg
     * url : http://w7sp.com/?m=vod-detail-id-8821.html
     * type : 最新电影
     * source : null
     * updateTime : 2018-04-01
     * definition : HD高清
     */

    private String id;
    private String title;
    private String img;
    private String url;
    private String type;
    private Object source;
    private String updateTime;
    private String definition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
