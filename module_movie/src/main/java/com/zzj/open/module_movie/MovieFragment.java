package com.zzj.open.module_movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_movie.databinding.MovieFragmentMovieBinding;
import com.zzj.open.module_movie.viewmodel.MovieViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/13 9:44
 * @desc :http://123.207.150.253/ygcms/getLineMovie.php?type=科幻&page=1&pagesize=18
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Movie.MOVIE_HOME)
public class MovieFragment extends BaseFragment<MovieFragmentMovieBinding,MovieViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.movie_fragment_movie;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.requestNetWork();
    }

}
