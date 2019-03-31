package com.zzj.module_welfare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.zzj.module_welfare.R;
import com.zzj.module_welfare.databinding.WelfareActivityImagedetailsBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 23:45
 * @desc : 图片详情
 * @version: 1.0
 */
public class WelfareImageDetailsActivity extends BaseActivity<WelfareActivityImagedetailsBinding, BaseViewModel> {
    private String url;


    public static void start(Context context, String url) {
        Intent starter = new Intent(context, WelfareImageDetailsActivity.class);
        starter.putExtra("url", url);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.welfare_activity_imagedetails;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();

        url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).into(binding.photoView);
        // 启用图片缩放功能
        binding.photoView.enable();
// 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)
//        photoView.disenable();
// 获取图片信息
//        Info info = binding.photoView.getInfo();
//// 从普通的ImageView中获取Info
//        Info info = PhotoView.getImageViewInfo(ImageView);
//// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
//        photoView.animaFrom(info);
//// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照demo的使用
//        photoView.animaTo(info,new Runnable() {
//            @Override
//            public void run() {
//                //动画完成监听
//            }
//        });
//// 获取/设置 动画持续时间
//        photoView.setAnimaDuring(int during);
//        int d = binding.photoView.getAnimaDuring();
        // 获取/设置 最大缩放倍数
        binding.photoView.setMaxScale(3);
    }
}
