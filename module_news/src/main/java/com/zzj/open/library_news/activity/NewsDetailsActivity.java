package com.zzj.open.library_news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzj.open.library_news.R;
import com.zzj.open.library_news.databinding.NewsActivityDetailsNewsBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/8 22:10
 * @desc :  新闻详情页
 * @version: 1.0
 */
public class NewsDetailsActivity extends BaseActivity<NewsActivityDetailsNewsBinding,BaseViewModel>{

    private String url;

    public static void start(Context context,String url) {
        Intent starter = new Intent(context, NewsDetailsActivity.class);
        starter.putExtra("url",url);
        context.startActivity(starter);
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.news_activity_details_news;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        url = getIntent().getStringExtra("url");
        binding.webView.loadUrl(url);
    }
}
