package com.zzj.open.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.xcy8.ads.listener.OnPermissionListener;
import com.xcy8.ads.view.BannerAdView;
import com.xcy8.ads.view.IconAd;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_mine.BR;
import com.zzj.open.module_mine.databinding.MineFragmentMineBinding;
import com.zzj.open.module_mine.viewmodel.MineViewModel;

import cdc.sed.yff.nm.cm.ErrorCode;
import cdc.sed.yff.nm.sp.SpotListener;
import cdc.sed.yff.nm.sp.SpotManager;
import me.goldze.mvvmhabit.base.BaseFragment;
import skin.support.SkinCompatManager;

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

    @Override
    public void initData() {
        super.initData();

        binding.llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.start(getActivity());
            }
        });

        binding.llSwitchSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 后缀加载
//                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                // 恢复应用默认皮肤
//                SkinCompatManager.getInstance().restoreDefaultTheme();
            }
        });

    }
}
