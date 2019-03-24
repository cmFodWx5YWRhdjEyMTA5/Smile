package com.zzj.open.module_movie;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
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
    //搜索内容
    private String content;
    public static MovieFragment newInstance(String type,String content) {
        
        Bundle args = new Bundle();
        
        MovieFragment fragment = new MovieFragment();
        args.putString("type",type);
        args.putString("content",content);
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
        setSwipeBackEnable(false);
        initListener();
        type = getArguments().getString("type");
        if(type.equals("搜索")){
            content = getArguments().getString("content");
            viewModel.search(content);
        }else {
            viewModel.requestNetWork(type,page);

        }

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

        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
            }
        });
    }

}
