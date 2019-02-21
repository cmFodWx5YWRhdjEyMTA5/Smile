package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_movie.BR;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.api.MovieApiService;
import com.zzj.open.module_movie.bean.MovieBean;

import java.util.List;

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
        public ObservableBoolean finishRefreshing = new ObservableBoolean(true);
        /**
         * 上拉加载完成
         */
        public ObservableBoolean finishLoadmore = new ObservableBoolean(true);
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableArrayList<MovieItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<MovieItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.movie_list_item);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<MovieItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public void requestNetWork(String type, final int page) {
        RetrofitClient.getInstance().create(MovieApiService.class)
                .getNesMovieList(type,page,12)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showDialog("正在请求...");
                    }
                }).subscribe(new Consumer<Result<List<MovieBean>>>() {
            @Override
            public void accept(Result<List<MovieBean>> movieBean) throws Exception {
                uc.finishLoadmore.set(!uc.finishLoadmore.get());
                if(movieBean.getCode() == 1){

                    if(page == 0){
                        observableList.clear();
                    }
                    for (MovieBean dataBean : movieBean.getResult()) {

                        MovieItemViewModel itemViewModel = new MovieItemViewModel(MovieViewModel.this, dataBean);
                        //双向绑定动态添加Item
                        observableList.add(itemViewModel);
                    }
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                uc.finishLoadmore.set(!uc.finishLoadmore.get());
//                dismissDialog();
                ToastUtils.showShort(throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                uc.finishLoadmore.set(!uc.finishLoadmore.get());
                dismissDialog();

            }
        });
    }

}
