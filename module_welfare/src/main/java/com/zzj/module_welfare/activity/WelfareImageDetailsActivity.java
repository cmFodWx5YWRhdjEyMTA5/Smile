package com.zzj.module_welfare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
public class WelfareImageDetailsActivity extends BaseActivity<WelfareActivityImagedetailsBinding,BaseViewModel> {
    private String url;

    public static void start(Context context,String url) {
        Intent starter = new Intent(context, WelfareImageDetailsActivity.class);
        starter.putExtra("url",url);
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
//        Glide.with(this).load(url).into(binding.photoView);
    }
}
