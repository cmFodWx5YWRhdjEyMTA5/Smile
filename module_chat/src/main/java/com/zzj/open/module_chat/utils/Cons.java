package com.zzj.open.module_chat.utils;

/**
 * Created by zzj on 2018/2/7.
 */

public interface Cons {
    //参数一
    public static final String PARAMETER_ONE = "PARAMETER_ONE";
    //参数二
    public static final String PARAMETER_TWO = "PARAMETER_TWO";
    //高德地图key
    public static final String GAODEMAP_KEY = "cef866a076f8395bf4ce9dc451e2647e";


    public static int EMOTICON_CLICK_TEXT = 1;
    public static int EMOTICON_CLICK_BIGIMAGE = 2;

    // 最大的上传图片大小860kb
    long MAX_UPLOAD_IMAGE_LENGTH = 860 * 1024;
    /**
     * 图片选择器
     */
    int IMAGE_PICKER = 100;

    class SaveKey{
        public static final String PHONE = "phone";
        public static final String USERNAME = "username";
        public static final String USER_ID = "userid";
        public static final String PASSWORD = "password";
        public static final String USER_ADDRESS = "address";
        public static final String USER_HEADER_PIC = "headerpic";
        public static final String USER_COIN = "user_coin";
        public static final String USER_INTEGRAL = "user_integral";
        //经纬度
        public static final String USER_LATITUDE = "user_Latitude";
        public static final String USER_LONGITUDE = "user_Longitude";
        //是否第一次启动
        public static final String IS_FIRST_START = "IS_FIRST_START";

    }
}
