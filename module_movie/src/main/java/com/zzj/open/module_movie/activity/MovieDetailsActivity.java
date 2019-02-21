package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
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
public class MovieDetailsActivity extends BaseActivity<MovieActivityMovieDetailsBinding, MovieDetailsViewModel> {

    private MovieBean dataBean;

    public static void start(Context context, MovieBean dataBean) {
        Intent starter = new Intent(context, MovieDetailsActivity.class);
        starter.putExtra("dataBean", dataBean);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                String url ="";
                String title = "";
                if(result.getType().equals("电影片")){
                   url = result.getPlayUrl();
                    title = result.getTitle();
                }else {
                    if(result.getPlayUrls()!=null&&result.getPlayUrls().size()>0){
                        String data= result.getPlayUrls().get(0).getPlayUrl();
                        if(data.contains("$")){
                            url =   data.split("\\$")[1];
                            title = data.split("\\$")[0];
                        }
                    }
                }
                binding.videoplayer.setUp(url
                        ,title, JzvdStd.SCREEN_WINDOW_NORMAL);
            }

            @Override
            public void fails(String error) {

            }
        });
        Glide.with(this).load(dataBean.getImg()).into(binding.videoplayer.thumbImageView);

        viewModel.playUrl.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //直接全屏播放
                JzvdStd.startFullscreen(MovieDetailsActivity.this, JzvdStd.class,viewModel.playUrl.get().getUrl(),viewModel.playUrl.get().getTitle());


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //home back
        JzvdStd.goOnPlayOnResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //home back
        JzvdStd.goOnPlayOnPause();

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
