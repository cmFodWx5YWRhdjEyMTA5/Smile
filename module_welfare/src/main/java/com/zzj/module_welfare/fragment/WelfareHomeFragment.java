package com.zzj.module_welfare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.module_welfare.BR;
import com.zzj.module_welfare.R;
import com.zzj.module_welfare.databinding.WelfareFragmentHomeBinding;
import com.zzj.open.base.router.RouterFragmentPath;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 12:39
 * @desc :
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Welfare.WELFARE_HOME)
public class WelfareHomeFragment extends BaseFragment<WelfareFragmentHomeBinding,BaseViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.welfare_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
