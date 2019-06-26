package com.zzj.open.module_lvji.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.adapter.LvjiHomeDynamicAdapter;
import com.zzj.open.module_lvji.databinding.LvjiFragmentNearbyBinding;
import com.zzj.open.module_lvji.viewmodel.LvJiHomeViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/18
 * @desc : 附近
 * @version: 1.0
 */
public class LvjiNearbyFragment extends BaseFragment<LvjiFragmentNearbyBinding, LvJiHomeViewModel> {

    private LvjiHomeDynamicAdapter dynamicAdapter;
    private int page = 0;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_nearby;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        viewModel.getPublishList(page);
        dynamicAdapter = new LvjiHomeDynamicAdapter(viewModel.lvjiPublishModels.get());
        binding.recyclerView.setAdapter(dynamicAdapter);
        initListener();
        initTopLayout();
    }

    private void initTopLayout() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.lvji_include_topic_top_recycler_view, binding.recyclerView,false);
        dynamicAdapter.addHeaderView(view);
    }

    private void initListener() {
        /**
         * 监听请求的数据变化
         */
        viewModel.lvjiPublishModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                if (page == 0) {
                    dynamicAdapter.setNewData(viewModel.lvjiPublishModels.get());
                } else {
                    dynamicAdapter.addData(viewModel.lvjiPublishModels.get());
                }
            }
        });

        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                viewModel.getPublishList(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                viewModel.getPublishList(page);
            }
        });

        /**
         * 网络请求结束监听
         */
        viewModel.isRefresh.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.refresh.finishRefresh();
                binding.refresh.finishLoadMore();
            }
        });
    }
}
