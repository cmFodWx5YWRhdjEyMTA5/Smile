package com.zzj.open.module_lvji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.databinding.LvjiFragmentCreateTopBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/22
 * @desc : 创建话题
 * @version: 1.0
 */
public class LvJiCreateTopicFragment extends BaseFragment<LvjiFragmentCreateTopBinding, BaseViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_create_top;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
