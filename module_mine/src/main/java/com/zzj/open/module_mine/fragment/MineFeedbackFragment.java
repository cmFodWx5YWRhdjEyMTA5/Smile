package com.zzj.open.module_mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_mine.R;
import com.zzj.open.module_mine.databinding.MineFragmentFeedbackBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/9 15:45
 * @desc :反馈建议页面
 * @version: 1.0
 */
public class MineFeedbackFragment extends BaseFragment<MineFragmentFeedbackBinding,BaseViewModel>{
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.mine_fragment_feedback;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"反馈建议");
    }
}
