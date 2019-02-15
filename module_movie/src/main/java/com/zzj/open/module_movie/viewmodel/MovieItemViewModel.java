package com.zzj.open.module_movie.viewmodel;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.activity.MovieDetailsActivity;
import com.zzj.open.module_movie.bean.MovieBean;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author JamesZhang
 */
public class MovieItemViewModel extends ItemViewModel<MovieViewModel> {
    public MovieBean.DataBean dataBean;
    public Drawable drawableImg;
    public MovieItemViewModel(@NonNull MovieViewModel viewModel,MovieBean.DataBean dataBean) {
        super(viewModel);
        this.dataBean = dataBean;
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);

    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {


            MovieDetailsActivity.start(viewModel.getApplication(),dataBean);
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
