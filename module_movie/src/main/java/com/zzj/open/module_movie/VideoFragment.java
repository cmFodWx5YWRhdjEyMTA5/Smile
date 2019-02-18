package com.zzj.open.module_movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_movie.databinding.MovieFragmentVideoBinding;
import com.zzj.open.module_movie.viewmodel.VideoViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/18 12:32
 * @desc : 影视视频类
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Movie.MOVIE_HOME)
public class VideoFragment extends BaseFragment<MovieFragmentVideoBinding,VideoViewModel> {

    private List<Fragment> fragmentList = new ArrayList<>();
    private VideoFragmentAdapter adapter;
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.movie_fragment_video;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        fragmentList.add(MovieFragment.newInstance());
        fragmentList.add(MovieFragment.newInstance());
        fragmentList.add(MovieFragment.newInstance());
        adapter = new VideoFragmentAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }


    public class VideoFragmentAdapter extends FragmentPagerAdapter{
        private  final String[] mTitles = {"电影", "电视剧", "综艺"};

        public VideoFragmentAdapter(FragmentManager fm) {
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
