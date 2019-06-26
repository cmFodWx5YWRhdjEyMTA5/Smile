package com.zzj.open.base.http;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/29 18:43
 * @desc :
 * @version: 1.0
 */
public class HttpUrl {
    //base
    public static final String BASE_URL = "http://106.12.22.215";
    //分布式文件地址
    public static final String IMAGE_URL = BASE_URL+":88/bengshiwei/";
    //bengshiwei
    public static String BSW_API_URL = BASE_URL+":8080/bengshiwei/api/";
    //服务端根路径
    public static String API_URL = BASE_URL+":8080/";
    //StartRTC消息服务
    public static String RTC_IM_SERVER = "106.12.22.215:19903";
    //StartRTC聊天室服务
    public static String RTC_CHATROOM_SERVER = "106.12.22.215:19906";
    //liveSrc服务
    public static String RTC_LIVESRC_SERVER = "106.12.22.215:19931";
    //liveVdn服务
    public static String RTC_LIVEVDN_SERVER = "106.12.22.215:19925";
    //voip服务
    public static String RTC_VOIP_SERVER = "47.75.50.156:10086";
}
