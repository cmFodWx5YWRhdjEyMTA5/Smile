package com.zzj.open.module_main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.databinding.MainFragmentMainBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.recyclerview.BR;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/24 12:00
 * @desc :
 * @version: 1.0
 */
public class MainFragment extends BaseFragment<MainFragmentMainBinding,BaseViewModel> {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUTR = 3;
    private BaseFragment[] mFragments = new BaseFragment[4];
    private int hideFragment = 0;//隐藏的fragment
    private int showFragment = 0;//需要显示的fragment
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.main_fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.videModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);
        getFragments();
        binding.tabBottom.setMode(BottomNavigationBar.MODE_FIXED);
        binding.tabBottom.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        binding.tabBottom.setBarBackgroundColor(R.color.white);
        binding.tabBottom.addItem(new BottomNavigationItem(R.mipmap.ic_movie, "首页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_news, "联系人").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_movie, "影视").setActiveColorResource(R.color.colorPrimary))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_news, "资讯").setActiveColorResource(R.color.colorPrimary))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_news, "福利").setActiveColorResource(R.color.amber_800))
                .addItem(new BottomNavigationItem(R.mipmap.ic_mine, "我的").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
        binding.tabBottom.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showFragment = position;
                showHideFragment(mFragments[showFragment], mFragments[hideFragment]);
                hideFragment = position;
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
    private void getFragments() {
//        BaseFragment firstFragment = findFragment((BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Movie.MOVIE_HOME).navigation());
//        if (firstFragment == null) {
        mFragments[FIRST] = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Chat.CHAT_HOME).navigation();
        mFragments[SECOND] = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Chat.CHAT_CONTACT).navigation();
        mFragments[THIRD] = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Movie.MOVIE_HOME).navigation();
//        mFragments[THIRD] = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.News.NEWS_HOME).navigation();
        mFragments[FOUTR] = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Mine.MINE_HOME).navigation();

        loadMultipleRootFragment(R.id.fl_container, FIRST,
                mFragments[FIRST],
                mFragments[SECOND],
                mFragments[THIRD],
                mFragments[FOUTR]);
//        } else {
//            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
//
//            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
//            mFragments[FIRST] = firstFragment;
//            mFragments[SECOND] = (PresenterFragment) findChildFragment(ChatListFragment.class);
//            mFragments[THIRD] = (PresenterFragment) findChildFragment(ContactFragment.class);
//            mFragments[FOUR] = (PresenterFragment) findChildFragment(MineFragment.class);
//        }
//        items.add((BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Movie.MOVIE_HOME).navigation());
//        items.add((BaseFragment) ARouter.getInstance().build(RouterFragmentPath.News.NEWS_HOME).navigation());
////        items.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Welfare.WELFARE_HOME).navigation());
//
//        items.add((BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Mine.MINE_HOME).navigation());

    }
}
