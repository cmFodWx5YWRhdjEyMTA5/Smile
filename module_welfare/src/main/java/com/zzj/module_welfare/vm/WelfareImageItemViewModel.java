package com.zzj.module_welfare.vm;

import android.support.annotation.NonNull;

import com.zzj.module_welfare.bean.GanKImageBean;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 19:15
 * @desc :
 * @version: 1.0
 */
public class WelfareImageItemViewModel extends ItemViewModel<WelfareImageViewModel> {

    public GanKImageBean.ResultsBean resultsBean;
    public WelfareImageItemViewModel(@NonNull WelfareImageViewModel viewModel, GanKImageBean.ResultsBean resultsBean) {
        super(viewModel);
        this.resultsBean = resultsBean;
    }
}
