package com.zzj.open.module_movie.bean;

import java.io.Serializable;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/19 20:41
 * @desc : 视频详情数据类
 * @version: 1.0
 */
public class MovieDetailsBean implements Serializable {


    /**
     * id : 8108ff4b-04f2-4e73-ad04-00f330b98b2a
     * movieId : 62a4dec9-3105-4e6c-9556-7c6429abdf40
     * title : null
     * img : null
     * url : http://w7sp.com/?m=vod-play-id-10008-src-1-num-1.html
     * playUrl : bWFjX3VybD11bmVzY2FwZSgnSEQldTlhZDgldTZlMDUlMjRodHRwJTNBJTJGJTJGd3d3LmxlLmNvbSUyRnB0diUyRnZwbGF5JTJGMzE1MzQwMzkuaHRtbCcpOw==
     * type : 喜剧片
     * source : null
     * updateTime : null
     * definition : null
     * director : 罗登
     * actor : 葛优,岳云鹏
     * region : 大陆
     * movieDesc : 卖鱼大叔牙叔（葛优 饰）、海豚训练员阿乐（岳云鹏 饰）和海洋馆保安治国（杜淳 饰）因生活中的不顺聚在海洋馆喝酒，大醉之后竟然把海洋馆大明星海豚“宝宝”卖到了国外。为找回海豚宝宝，三人辗转多地，遇到各种奇人怪事，发生了许多令人啼笑皆非的故事。
     * movieType : 0
     */

    private String id;
    private String movieId;
    private Object title;
    private Object img;
    private String url;
    private String playUrl;
    private String type;
    private Object source;
    private Object updateTime;
    private Object definition;
    private String director;
    private String actor;
    private String region;
    private String movieDesc;
    private int movieType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
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

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getDefinition() {
        return definition;
    }

    public void setDefinition(Object definition) {
        this.definition = definition;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }
}
