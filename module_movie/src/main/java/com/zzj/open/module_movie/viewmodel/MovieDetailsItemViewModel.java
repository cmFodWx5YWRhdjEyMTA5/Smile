package com.zzj.open.module_movie.viewmodel;

import android.support.annotation.NonNull;

import com.zzj.open.module_movie.bean.MovieDetailsItemBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/21 19:45
 * @desc :
 * @version: 1.0
 */
public class MovieDetailsItemViewModel extends ItemViewModel<MovieDetailsViewModel> {
    public MovieDetailsItemBean dataBean;
    public MovieDetailsItemViewModel(@NonNull MovieDetailsViewModel viewModel,MovieDetailsItemBean dataBean) {
        super(viewModel);
        this.dataBean = dataBean;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.playUrl.set(dataBean);
        }
    });
}
