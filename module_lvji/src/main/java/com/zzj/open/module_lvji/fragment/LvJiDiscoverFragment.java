package com.zzj.open.module_lvji.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.databinding.LvjiFragmentDiscoverBinding;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ConvertUtils;

import static android.support.design.widget.TabLayout.MODE_FIXED;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/13 12:36
 * @desc :发现
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Lvji.PAGER_DISCOVER)
public class LvJiDiscoverFragment extends BaseFragment<LvjiFragmentDiscoverBinding, BaseViewModel> {

    private List<BaseFragment> fragments = new ArrayList<>();

    private String[] titles = new String[]{"附近","话题"};
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_discover;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);
        fragments.add(new LvjiNearbyFragment());
        fragments.add(new LvjiTopicFragment());

//        binding.tabSegment.setHasIndicator(true);
//        binding.tabSegment.setIndicatorPosition(false);
//        binding.tabSegment.setIndicatorWidthAdjustContent(true);
//        binding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.lj_colorAccent));
////        binding.tabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.lj_tab_indicator_bg));
//        binding.tabSegment.addTab(new QMUITabSegment.Tab("附近"));
//        binding.tabSegment.addTab(new QMUITabSegment.Tab("话题"));
//        binding.tabSegment.setItemSpaceInScrollMode(ConvertUtils.dp2px(20));
//        binding.tabSegment.getTab(0).setTextSize(ConvertUtils.dp2px(20));
//
//
//        //设置字体样式
//        binding.tabSegment.setTypefaceProvider(new QMUITabSegment.TypefaceProvider() {
//            @Override
//            public boolean isNormalTabBold() {
//                return false;
//            }
//
//            @Override
//            public boolean isSelectedTabBold() {
//                return true;
//            }
//
//            @Nullable
//            @Override
//            public Typeface getTypeface() {
//                return null;
//            }
//        });
//        binding.tabSegment.notifyDataChanged();
//        binding.tabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);

        binding.contentViewPager.setAdapter(new FragmentStatePagerAdapter(_mActivity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
//        binding.tabSegment.setupWithViewPager(binding.contentViewPager,false);

//        binding.tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int index) {
////                binding.tabSegment.getTab(index).setTextSize(ConvertUtils.dp2px(20));
////                binding.tabSegment.notifyDataChanged();
//            }
//
//            @Override
//            public void onTabUnselected(int index) {
////                binding.tabSegment.getTab(index).setTextSize(ConvertUtils.dp2px(16));
////                binding.tabSegment.notifyDataChanged();
//            }
//
//            @Override
//            public void onTabReselected(int index) {
//
//            }
//
//            @Override
//            public void onDoubleTap(int index) {
//
//            }
//        });

        CommonNavigator commonNavigator = new CommonNavigator(_mActivity);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.gray_text_color));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.lj_colorAccent));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.contentViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(getResources().getColor(R.color.lj_colorAccent));
                return indicator;
            }
        });
        binding.tabSegment.setNavigator(commonNavigator);
        ViewPagerHelper.bind( binding.tabSegment,  binding.contentViewPager);
    }
}
