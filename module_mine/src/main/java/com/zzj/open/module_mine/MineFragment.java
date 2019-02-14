package com.zzj.open.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_mine.BR;
import com.zzj.open.module_mine.databinding.MineFragmentMineBinding;
import com.zzj.open.module_mine.viewmodel.MineViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/14 14:18
 * @desc : 我的界面
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Mine.MINE_HOME)
public class MineFragment extends BaseFragment<MineFragmentMineBinding,MineViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.mine_fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
