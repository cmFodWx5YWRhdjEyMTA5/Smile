package com.zzj.module_welfare.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zzj.module_welfare.BR;
import com.zzj.module_welfare.R;
import com.zzj.module_welfare.databinding.WelfareFragmentImagesBinding;
import com.zzj.module_welfare.vm.WelfareImageViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 19:04
 * @desc : 图片
 * @version: 1.0
 */
public class WelfareImageFragment extends BaseFragment<WelfareFragmentImagesBinding,WelfareImageViewModel> {

    private int page  = 1;

    public static WelfareImageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WelfareImageFragment fragment = new WelfareImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.welfare_fragment_images;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
//设置layoutManager
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //解决item跳动
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recycler.setLayoutManager(manager);

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                manager.invalidateSpanAssignments();
            }
        });

        viewModel.requestNetWork(page);


        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                viewModel.requestNetWork(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                viewModel.requestNetWork(page);
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
