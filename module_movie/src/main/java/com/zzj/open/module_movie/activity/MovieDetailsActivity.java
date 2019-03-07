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

import cn.waps.AppConnect;
import cn.waps.AppListener;
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
    private FixedAdView mAd1Fav;
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
        // 预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
        AppConnect.getInstance(this).initAdInfo();
        AppConnect.getInstance(this).initPopAd(this);
        //调用方式 2：显示揑屏广告时设置关闭揑屏广告癿监听接口 (选用)
        AppConnect.getInstance(this).showPopAd(this, new AppListener(){
            @Override
            public void onPopClose() {super.onPopClose(); }
        });
// 设置互动广告无数据时的回调监听（该方法必须在showBannerAd之前调用）
        AppConnect.getInstance(this).setBannerAdNoDataListener(new AppListener() {

            @Override
            public void onBannerNoData() {
                Log.i("debug", "banner广告暂无可用数据");
            }

        });
        // 互动广告调用方式
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, layout);

        // 迷你广告调用方式
        // AppConnect.getInstance(this).setAdBackColor(Color.argb(50, 120, 240,
        // 120));//设置迷你广告背景颜色
        // AppConnect.getInstance(this).setAdForeColor(Color.YELLOW);//设置迷你广告文字颜色
        LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
        AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);// 10秒刷新一次
//        mAd1Bav = new BannerAdView(this);
//        mAd1Bav.setFloat(false);
//        mAd1Bav.loadAd(BANNER_AD_ID);
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

                if(TbsVideo.canUseTbsPlayer(getApplicationContext())){
                    Bundle bundle = new Bundle();
                    bundle.putInt("screenMode",103);
//                    bundle.putInt("DefaultVideoScreen", 2);
                    TbsVideo.openVideo(getApplicationContext(), movieDetailsItemBean.getUrl(),bundle);
                }
//                VideoPlayerActivity.start(MovieDetailsActivity.this,movieDetailsItemBean);

            }
        });
    }



}
