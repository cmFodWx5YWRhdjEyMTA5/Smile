package com.zzj.open.module_movie.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsVideo;
import com.xcy8.ads.view.BannerAdView;
import com.xcy8.ads.view.FixedAdView;
import com.zzj.open.base.bean.CallBack;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.bean.MovieDetailsBean;
import com.zzj.open.module_movie.bean.MovieDetailsItemBean;
import com.zzj.open.module_movie.databinding.MovieActivityMovieDetailsBinding;
import com.zzj.open.module_movie.viewmodel.MovieDetailsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;

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
    // FixedAd
    private static final String BANNER_AD_ID = "42705";
    private static final String FIXED_AD_ID = "42707";
    private BannerAdView mAd1Bav;
    private String movieId;
    public static void start(Context context, String movieId) {
        Intent starter = new Intent(context, MovieDetailsActivity.class);
        starter.putExtra("movieId", movieId);
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

        mAd1Bav = new BannerAdView(this);
        mAd1Bav.setFloat(false);
        mAd1Bav.loadAd(BANNER_AD_ID);
        movieId = getIntent().getStringExtra("movieId");
        String url = "";

        viewModel.getMovieDetais(movieId, new CallBack<MovieDetailsBean>() {
            @Override
            public void success(MovieDetailsBean result) {
//                String url ="";
//                String title = "";
//                if(result.getType().equals("电影片")){
//                   url = result.getPlayUrl();
//                    title = result.getTitle();
//                }else {
//                    if(result.getPlayUrls()!=null&&result.getPlayUrls().size()>0){
//                        String data= result.getPlayUrls().get(0).getPlayUrl();
//                        if(data.contains("$")){
//                            url =   data.split("\\$")[1];
//                            title = data.split("\\$")[0];
//                        }
//                    }
//                }
            }

            @Override
            public void fails(String error) {

            }
        });


        viewModel.playUrl.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                MovieDetailsItemBean movieDetailsItemBean = viewModel.playUrl.get();

//                if(TbsVideo.canUseTbsPlayer(getApplicationContext())){
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("screenMode",103);
////                    bundle.putInt("DefaultVideoScreen", 2);
//                    TbsVideo.openVideo(getApplicationContext(), "http://bili.meijuzuida.com/share/8deb8d1dd92840f975b6931ab3a3c61e",bundle);
//                }

                FullScreenActivity.start(MovieDetailsActivity.this,movieDetailsItemBean.getUrl());
//                VideoPlayerActivity.start(MovieDetailsActivity.this,movieDetailsItemBean);

            }
        });
    }



}
