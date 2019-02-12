package com.zzj.open.library_news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.library_news.BR;
import com.zzj.open.library_news.R;
import com.zzj.open.library_news.databinding.NewsFragmentNewsBinding;
import com.zzj.open.library_news.viewmodel.NewsViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/28 16:02
 * @desc :
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class NewsFragment extends BaseFragment<NewsFragmentNewsBinding,NewsViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.news_fragment_news;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.requestNetWork();
    }
}
