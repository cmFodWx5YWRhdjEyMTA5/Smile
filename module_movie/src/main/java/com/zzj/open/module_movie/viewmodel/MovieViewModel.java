package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.zzj.open.module_movie.BR;
import com.zzj.open.module_movie.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/13 9:47
 * @desc : 电影viewModel
 * @version: 1.0
 */
public class MovieViewModel extends BaseViewModel {

    public String title = "最新影视";

    /**
     * 封装一个界面发生改变的观察者
     */
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        /**
         * 下拉刷新完成
         */
        public ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        /**
         * 上拉加载完成
         */
        public ObservableBoolean finishLoadmore = new ObservableBoolean(false);
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableArrayList<MovieItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<MovieItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.movie_list_item);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<MovieItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
            requestNetWork();
        }



    });
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("上拉加载");

        }
    });

    private void requestNetWork() {
    }

}
