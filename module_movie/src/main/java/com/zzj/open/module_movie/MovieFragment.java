package com.zzj.open.module_movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_movie.databinding.MovieFragmentMovieBinding;
import com.zzj.open.module_movie.viewmodel.MovieViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/13 9:44
 * @desc :http://123.207.150.253/ygcms/getLineMovie.php?type=科幻&page=1&pagesize=18
 * @version: 1.0
 */

public class MovieFragment extends BaseFragment<MovieFragmentMovieBinding,MovieViewModel> {

    private String type;
    private int page = 0;
    public static MovieFragment newInstance(String type) {
        
        Bundle args = new Bundle();
        
        MovieFragment fragment = new MovieFragment();
        args.putString("type",type);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.movie_fragment_movie;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        initListener();
        type = getArguments().getString("type");
        viewModel.requestNetWork(type,page);
    }

    private void initListener(){

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                viewModel.requestNetWork(type,page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page = 0;
                viewModel.requestNetWork(type,page);
            }
        });
    }

}
