package com.zzj.open.library_news.viewmodel;

import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.zzj.open.library_news.R;
import com.zzj.open.library_news.bean.NewsBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/11 16:58
 * @desc :
 * @version: 1.0
 */
public class NewsItemViewModel extends ItemViewModel<NewsViewModel> {

    public NewsBean.ResultBean.DataBean dataBean;

    public Drawable drawableImg;
    public NewsItemViewModel(@NonNull NewsViewModel viewModel, NewsBean.ResultBean.DataBean dataBean) {
        super(viewModel);
        this.dataBean = dataBean;
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不能界面的逻辑
//            if (entity.getId() == -1) {
//                ToastUtils.showShort(entity.getName());
//            } else {
//                //跳转到详情界面,传入条目的实体对象
//                Bundle mBundle = new Bundle();
//                mBundle.putParcelable("entity", entity);
//                viewModel.startContainerActivity(DetailFragment.class.getCanonicalName(), mBundle);
//            }

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
