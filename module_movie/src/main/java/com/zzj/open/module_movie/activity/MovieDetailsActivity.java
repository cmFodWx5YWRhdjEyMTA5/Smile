package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;

import com.xcy8.ads.view.BannerAdView;
import com.zzj.open.base.bean.CallBack;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.bean.MovieDetailsBean;
import com.zzj.open.module_movie.bean.MovieDetailsItemBean;
import com.zzj.open.module_movie.databinding.MovieActivityMovieDetailsBinding;
import com.zzj.open.module_movie.viewmodel.MovieDetailsViewModel;

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
        new ToolbarHelper(this,binding.toolbar,true);
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
//                    TbsVideo.openVideo(getApplicationContext(), "https://baidu.com-l-baidu.com/20190301/12571_f91b07f1/index.m3u8",bundle);
//                }

                //https://boba.52kuyun.com/20190305/21189_973c9f6d/index.m3u8
                //第04集$https://bobo.kukucdn.com/share/6194a1ee187acd6606989f03769e8f7f
                FullScreenActivity.start(MovieDetailsActivity.this,movieDetailsItemBean.getUrl());

//                BaseWebActivity.start(MovieDetailsActivity.this,movieDetailsItemBean.getUrl());
//                VideoPlayerActivity.start(MovieDetailsActivity.this,movieDetailsItemBean);

            }
        });
    }



}
