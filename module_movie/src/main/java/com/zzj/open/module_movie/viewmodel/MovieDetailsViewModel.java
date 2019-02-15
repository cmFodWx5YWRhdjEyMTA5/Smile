package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.zzj.open.module_movie.bean.MovieBean;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/15 16:22
 * @desc : 影视详情的viewModel
 * @version: 1.0
 */
public class MovieDetailsViewModel extends BaseViewModel {
    public MovieBean.DataBean dataBean;
    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public MovieBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(MovieBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
