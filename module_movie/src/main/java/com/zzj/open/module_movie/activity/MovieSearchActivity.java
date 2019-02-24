package com.zzj.open.module_movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.zzj.open.module_movie.MovieFragment;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.databinding.MovieActivityMovieDetailsBinding;
import com.zzj.open.module_movie.databinding.MovieActivityMovieSearchBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/23 14:27
 * @desc : 视频搜索界面
 * @version: 1.0
 */
public class MovieSearchActivity extends BaseActivity<MovieActivityMovieSearchBinding,BaseViewModel> {

    private List<String> lastSearches;
    public static void start(Context context) {
        Intent starter = new Intent(context, MovieSearchActivity.class);
        context.startActivity(starter);
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.movie_activity_movie_search;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        lastSearches = new ArrayList<>();
        Set<String> stringSet = SPUtils.getInstance().getStringSet("local_search");
        if(stringSet!=null&&stringSet.size()>0){
            for(String s : stringSet){
                lastSearches.add(s);
            }
        }
        binding.searchBar.setLastSuggestions(lastSearches);
        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                MovieFragment fragment = MovieFragment.newInstance("搜索",text.toString());
                FragmentUtils.replace(getSupportFragmentManager(),fragment,R.id.fl_container);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Set<String> strings = new HashSet<>();
        if(binding.searchBar.getLastSuggestions().size()>0){
            for(Object s : binding.searchBar.getLastSuggestions()){
                strings.add((String) s);
            }
        }
        //保存搜索记录
        SPUtils.getInstance().put("local_search",strings);
    }
}
