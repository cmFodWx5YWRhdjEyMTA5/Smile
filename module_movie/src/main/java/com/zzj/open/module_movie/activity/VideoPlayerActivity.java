package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xcy8.ads.view.BannerAdView;
import com.zzj.open.base.base.BaseModuleInit;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.bean.MovieDetailsItemBean;
import com.zzj.open.module_movie.databinding.MovieActivityVideoPlayerBinding;
import com.zzj.open.module_movie.player.JZMediaIjkplayer;

import java.util.LinkedHashMap;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/23 21:52
 * @desc :视频播放页
 * @version: 1.0
 */
public class VideoPlayerActivity extends BaseActivity<MovieActivityVideoPlayerBinding,BaseViewModel> {
    private static final String BANNER_AD_ID = "42705";
    private BannerAdView mAd1Bav;
    private MovieDetailsItemBean playUrlsBean;

    public static void start(Context context, MovieDetailsItemBean playUrlsBean) {
        Intent starter = new Intent(context, VideoPlayerActivity.class);
        starter.putExtra("playUrl", playUrlsBean);
        context.startActivity(starter);
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.movie_activity_video_player;
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
        playUrlsBean = (MovieDetailsItemBean) getIntent().getSerializableExtra("playUrl");

//        LinkedHashMap map = new LinkedHashMap();
//
//        String proxyUrl = ApplicationDemo.getProxy(this).getProxyUrl(VideoConstant.videoUrls[0][9]);
//
//        map.put("高清", proxyUrl);
//        map.put("标清", VideoConstant.videoUrls[0][6]);
//        map.put("普清", VideoConstant.videoUrlList[0]);
//        JZDataSource jzDataSource = new JZDataSource(map, "饺子不信");
//        jzDataSource.looping = true;
//        jzDataSource.currentUrlIndex = 2;
//

        LinkedHashMap map = new LinkedHashMap();

        //边播放变缓存
        String proxyUrl = BaseModuleInit.getProxy().getProxyUrl(playUrlsBean.getUrl());

        map.put("高清", proxyUrl);

        JZDataSource jzDataSource = new JZDataSource(playUrlsBean.getUrl());
        jzDataSource.title = playUrlsBean.getTitle();
        jzDataSource.looping = true;
        jzDataSource.headerMap.put("key", "value");//header
        binding.videoplayer.setUp(jzDataSource, Jzvd.SCREEN_WINDOW_NORMAL);
        //        //直接全屏播放
//        JzvdStd.startFullscreen(VideoPlayerActivity.this, JzvdStd.class,playUrlsBean.getUrl(),playUrlsBean.getTitle());

        Jzvd.setMediaInterface(new JZMediaIjkplayer());  //  ijkMediaPlayer
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JzvdStd.releaseAllVideos();
    }
}
