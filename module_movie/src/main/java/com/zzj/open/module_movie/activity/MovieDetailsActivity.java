package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.databinding.MovieActivityMovieDetailsBinding;
import com.zzj.open.module_movie.viewmodel.MovieDetailsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private MovieBean.DataBean dataBean;

    public static void start(Context context,MovieBean.DataBean dataBean) {
        Intent starter = new Intent(context, MovieDetailsActivity.class);
        starter.putExtra("dataBean",dataBean);
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
        dataBean = (MovieBean.DataBean) getIntent().getSerializableExtra("dataBean");
        String url = "";
        try {
            JSONObject jsonObject = new JSONObject(dataBean.getDownLoadUrl());
            JSONArray array = jsonObject.optJSONArray("m3u8");
            if(array!=null&&array.length()>0){
                JSONObject object = array.optJSONObject(0);
                url = object.optString("url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.videoplayer.setUp(url
                , dataBean.getDownLoadName(), JzvdStd.SCREEN_WINDOW_NORMAL);
        LogUtils.e("播放地址--》"+dataBean.getDownLoadUrl());

        Glide.with(this).load(dataBean.getDownimgurl()).into(binding.videoplayer.thumbImageView);
    }
}
