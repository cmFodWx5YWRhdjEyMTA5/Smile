package com.zzj.module_welfare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.zzj.module_welfare.BR;
import com.zzj.module_welfare.R;
import com.zzj.module_welfare.databinding.WelfareFragmentHomeBinding;
import com.zzj.module_welfare.vm.ViewPagerViewModel;
import com.zzj.open.base.router.RouterFragmentPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 12:39
 * @desc :福利首页
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Welfare.WELFARE_HOME)
public class WelfareHomeFragment extends BaseFragment<WelfareFragmentHomeBinding,ViewPagerViewModel> {

    private List<Fragment> fragmentList = new ArrayList<>();
    private WelfareFragmentAdapter adapter;
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
        fragmentList.add(WelfareImageFragment.newInstance());
        fragmentList.add(WelfareImageFragment.newInstance());
        adapter = new WelfareFragmentAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    public class WelfareFragmentAdapter extends FragmentPagerAdapter {

        private  final String[] mTitles = {"美图", "搞笑段子"};

        public WelfareFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
