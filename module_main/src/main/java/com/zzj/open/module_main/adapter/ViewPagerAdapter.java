package com.zzj.open.module_main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/3 10:17
 * @desc : BaseViewPagerActivity的适配器
 * @version: 1.0
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    private List<String> mTitles;
    private FragmentManager fm;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> lists){
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        fragments.addAll(lists);
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> lists, List<String> titles) {
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        fragments.addAll(lists);
        mTitles.addAll(titles);
    }

//    @NonNull
////    @Override
////    public Object instantiateItem(@NonNull ViewGroup container, int position) {
////        return super.instantiateItem(container, position);
////
////    }

//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
//            FragmentTransaction ft=fm.beginTransaction();
//            ft.remove((BaseFragment) object);
//            ft.commit();
//
//    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
