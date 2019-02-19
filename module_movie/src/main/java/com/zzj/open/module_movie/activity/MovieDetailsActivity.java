package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.zzj.open.base.bean.CallBack;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.bean.MovieDetailsBean;
import com.zzj.open.module_movie.databinding.MovieActivityMovieDetailsBinding;
import com.zzj.open.module_movie.viewmodel.MovieDetailsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/15 16:21
 * @desc :
 * @version: 1.0
 */
public class MovieDetailsActivity extends BaseActivity<MovieActivityMovieDetailsBinding,MovieDetailsViewModel> {

    private MovieBean dataBean;

    public static void start(Context context,MovieBean dataBean) {
        Intent starter = new Intent(context, MovieDetailsActivity.class);
        starter.putExtra("dataBean",dataBean);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(starter);
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.movie_activity_movie_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        dataBean = (MovieBean) getIntent().getSerializableExtra("dataBean");
        viewModel.setDataBean(dataBean);
        String url = "";

        viewModel.getMovieDetais(dataBean.getId(), new CallBack<MovieDetailsBean>() {
            @Override
            public void success(MovieDetailsBean result) {
                String url = new String(EncodeUtils.base64Decode(result.getPlayUrl()));
                LogUtils.e("getMovieDetais--->"+ url);
                String burl = convertPercent(url);
                String curl = null;
                curl = URLDecoder.decode(burl);
                LogUtils.e("getMovieDetais--->"+ curl);
                Matcher matcher = Patterns.WEB_URL.matcher(curl);
                if (matcher.find()){
//                    System.out.println(matcher.group());
                    String purl =  URLDecoder.decode(convertPercent(matcher.group()));

                    binding.videoplayer.setUp(purl.substring(0,purl.length()-3)
                , dataBean.getTitle(), JzvdStd.SCREEN_WINDOW_NORMAL);
                    LogUtils.e("播放地址--》"+ purl.substring(0,purl.length()-3));


                }


            }

            @Override
            public void fails(String error) {

            }
        });
        Glide.with(this).load(dataBean.getImg()).into(binding.videoplayer.thumbImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    //判断是否为16进制数
    public static boolean isHex(char c){
        if(((c >= '0') && (c <= '9')) ||
                ((c >= 'a') && (c <= 'f')) ||
                ((c >= 'A') && (c <= 'F')))
            return true;
        else
            return false;
    }

    public static String convertPercent(String str){
        StringBuilder sb = new StringBuilder(str);

        for(int i = 0; i < sb.length(); i++){
            char c = sb.charAt(i);
            //判断是否为转码符号%
            if(c == '%'){
                if(((i + 1) < sb.length() -1) && ((i + 2) < sb.length() - 1)){
                    char first = sb.charAt(i + 1);
                    char second = sb.charAt(i + 2);
                    //如只是普通的%则转为%25
                    if(!(isHex(first) && isHex(second)))
                        sb.insert(i+1, "25");
                }
                else{//如只是普通的%则转为%25
                    sb.insert(i+1, "25");
                }

            }
        }

        return sb.toString();
    }

}
