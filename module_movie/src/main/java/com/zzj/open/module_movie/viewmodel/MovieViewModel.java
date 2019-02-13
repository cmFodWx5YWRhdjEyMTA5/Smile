package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/13 9:47
 * @desc : 电影viewModel
 * @version: 1.0
 */
public class MovieViewModel extends BaseViewModel {

    public String title = "最新影视";
    public MovieViewModel(@NonNull Application application) {
        super(application);
    }
}
