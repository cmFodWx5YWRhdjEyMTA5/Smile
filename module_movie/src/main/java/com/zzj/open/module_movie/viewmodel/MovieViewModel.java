package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_movie.BR;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.api.MovieApiService;
import com.zzj.open.module_movie.bean.MovieBean;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
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

    public void requestNetWork() {
        RetrofitClient.getInstance().create(MovieApiService.class)
                .getMovieList("科幻",1,18)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                }).subscribe(new Consumer<MovieBean>() {
            @Override
            public void accept(MovieBean movieBean) throws Exception {
                for (MovieBean.DataBean dataBean : movieBean.getData()) {
                    MovieItemViewModel itemViewModel = new MovieItemViewModel(MovieViewModel.this, dataBean);
                    //双向绑定动态添加Item
                    observableList.add(itemViewModel);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissDialog();
                ToastUtils.showShort(throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();

            }
        });
    }

}
