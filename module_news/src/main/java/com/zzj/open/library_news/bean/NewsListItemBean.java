package com.zzj.open.library_news.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/8 22:26
 * @desc :  新闻列表的item实体类
 * @version: 1.0
 */
public class NewsListItemBean implements Serializable {


    /**
     * id : 2aa7ddc2-8dc4-458b-a3c8-d57682b92f2a
     * title : 71岁老人高速上推车逆行 泰安高速交警安全护送
     * img : http://n.sinaimg.cn/sd/336/w650h486/20190308/-f28-htwhfzt3596465.jpg
     * url : http://sd.sina.com.cn/news/2019-03-08/detail-ihrfqzkc2149383.shtml
     * type : shandong
     * source : null
     */

    private String id;
    private String title;
    private String img;
    private String url;
    private String type;
    private Object source;

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
}
