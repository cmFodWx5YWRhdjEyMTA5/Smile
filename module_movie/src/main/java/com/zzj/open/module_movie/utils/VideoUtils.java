package com.zzj.open.module_movie.utils;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/18 15:41
 * @desc :
 * @version: 1.0
 */
public class VideoUtils {
    //bWFjX3VybD11bmVzY2FwZSgnSEQldTlhZDgldTZlMDUlMjQ2NTEyMzM5NDQnKTs=

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getMovieUrl(){
        String baseUrl = new String(java.util.Base64.getDecoder().decode("bWFjX3VybD11bmVzY2FwZSgnSEQldTlhZDgldTZlMDUlMjRodHRwcyUzQSUyRiUyRmNzdmMucW5zZGsuY29tJTJGcHJvamVjdCUyRmQ5MWJlNWNhLTI0NDctNGM4Yy1hYjM1LTg0YmRiMDc3MDU5YyUyRiV1NTk4NyV1NzlkMSV1NTkxNiV1NjYxZiV1NGViYS5tcDQnKTs=")).toString();
        System.out.println("baseUrl--->"+baseUrl.toString());
        try {
            String urlStr = URLDecoder.decode(baseUrl);
            System.out.println(urlStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args){
        getMovieUrl();
    }
}
