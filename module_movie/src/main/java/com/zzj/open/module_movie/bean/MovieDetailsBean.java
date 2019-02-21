package com.zzj.open.module_movie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/19 20:41
 * @desc : 视频详情数据类
 * @version: 1.0
 */
public class MovieDetailsBean implements Serializable {


    /**
     * id : 015349c2-ec44-4f22-973f-d8f61198250f
     * title : 十全八美第一季更新到13集
     * img : https://tupian.tupianzy.com/pic/upload/vod/2018-10-18/201810181539876671.jpg
     * url : http://www.zuidazyw.com/?m=vod-detail-id-53914.html
     * playUrl : 1
     * playUrls : [{"id":"b852e853-d788-47b1-9619-36c98a2896e1","playUrl":"第06集$http://iqiyi.qq-zuidazy.com/20181130/1915_2fe23316/index.m3u8"},{"id":"74580f5e-98c5-4247-8562-a79cd5d78a97","playUrl":"第03集$http://youku163.zuida-bofang.com/20181102/19593_481ed90d/index.m3u8"},{"id":"068ca825-e441-497f-ae2b-aa5f04827267","playUrl":"第11集$http://youku163.zuida-bofang.com/20190127/26868_bf2cba43/index.m3u8"},{"id":"0ad0225b-c517-4d7e-8789-d79da205298f","playUrl":"第12集$http://bili.meijuzuida.com/20190219/5011_b4a01f05/index.m3u8"},{"id":"4339531f-509e-48b2-9129-b26da23a69a1","playUrl":"第02集$http://youku163.zuida-bofang.com/20181018/17765_33f0ec0e/index.m3u8"},{"id":"46426ed8-fc5f-4ed4-8052-9df6b382d617","playUrl":"第13集$http://bili.meijuzuida.com/20190219/5010_db7a5556/index.m3u8"},{"id":"9b9fdec2-697e-4dc3-87ec-c57a96ea37cc","playUrl":"第01集$http://youku163.zuida-bofang.com/20181025/18520_09f23d8d/index.m3u8"},{"id":"005d85fd-d978-46d9-84b2-c2cb496f585a","playUrl":"第10集$http://iqiyi.qq-zuidazy.com/20190120/4769_f08c3a13/index.m3u8"},{"id":"a650fa4e-f92d-4200-8d8f-14938338c3e2","playUrl":"第09集$http://iqiyi.qq-zuidazy.com/20190113/4513_aa2672a7/index.m3u8"},{"id":"7a263d0f-14e0-4344-9644-eaef0394234c","playUrl":"第04集$http://youku163.zuida-bofang.com/20181123/21159_bc743832/index.m3u8"},{"id":"817d2258-bf32-4b2d-8893-69640a3681c8","playUrl":"第08集$http://iqiyi.qq-zuidazy.com/20181217/3257_7dff176f/index.m3u8"},{"id":"d79ce155-6cb6-4734-9c83-d7347b6c0bb0","playUrl":"第05集$http://youku163.zuida-bofang.com/20181123/21161_453b1a66/index.m3u8"},{"id":"4ccf917e-863d-40c6-92ab-b96a3721e043","playUrl":"第07集$http://youku163.zuida-bofang.com/20181208/22176_fc51d02c/index.m3u8"}]
     * type : 连续剧
     * source : null
     * updateTime : 2019-02-19
     * definition : null
     * director : 兰德尔·艾因霍恩
     * actor : 玛丽·麦克科马克,迈克尔·库立兹,索耶·巴斯,Sam Straley
     * region : 美国
     * movieDesc : 由Tim Doyle开发的70年代家庭喜剧《The Kids Are Alright》讲述一个爱尔兰裔的天主教家庭 – Cleary一家。在美国最为动荡时期的洛杉矶工人阶级邻里中，Mike及Peggy照顾着8个孩子，不过这么多人也自然很难做到面面俱到。而当大儿子Lawrence回家说自己退出神学院时，这一家的平稳就被颠覆过来了。 Michael Cudlitz饰演Mike Cleary﹑Mary McCormack饰演Peggy Cleary﹑Sam Straley 饰演Lawrence﹑Caleb Martin Foote饰演Eddie﹑Sawyer Barth饰演Frank﹑Christopher Paul Richards饰演Joey﹑Jack Gore饰演Timmy﹑Andy Walken饰演William及Santino Barnard饰演Pat.。
     * movieType : 欧美剧
     */

    private String id;
    private String title;
    private String img;
    private String url;
    private String playUrl;
    private String type;
    private Object source;
    private String updateTime;
    private Object definition;
    private String director;
    private String actor;
    private String region;
    private String movieDesc;
    private String movieType;
    private List<PlayUrlsBean> playUrls;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public List<PlayUrlsBean> getPlayUrls() {
        return playUrls;
    }

    public void setPlayUrls(List<PlayUrlsBean> playUrls) {
        this.playUrls = playUrls;
    }

    public static class PlayUrlsBean {
        /**
         * id : b852e853-d788-47b1-9619-36c98a2896e1
         * playUrl : 第06集$http://iqiyi.qq-zuidazy.com/20181130/1915_2fe23316/index.m3u8
         */

        private String id;
        private String playUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
