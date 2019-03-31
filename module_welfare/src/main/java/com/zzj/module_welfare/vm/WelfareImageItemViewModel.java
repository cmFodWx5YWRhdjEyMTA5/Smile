package com.zzj.module_welfare.vm;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.zzj.module_welfare.R;
import com.zzj.module_welfare.activity.WelfareImageDetailsActivity;
import com.zzj.module_welfare.bean.GanKImageBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

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

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            viewModel.itemBean.set(resultsBean);
            WelfareImageDetailsActivity.start(viewModel.getApplication(),resultsBean.getUrl());

        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
//            viewModel.deleteItemLiveData.setValue(NetWorkItemViewModel.this);
        }
    });
}
