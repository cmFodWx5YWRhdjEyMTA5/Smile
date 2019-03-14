package com.zzj.module_welfare.vm;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.zzj.module_welfare.R;
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
    public Drawable drawableImg;
    public WelfareImageItemViewModel(@NonNull WelfareImageViewModel viewModel, GanKImageBean.ResultsBean resultsBean) {
        super(viewModel);
        this.resultsBean = resultsBean;
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_loading);
    }
}
