package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.CallBack;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_movie.api.MovieApiService;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.bean.MovieDetailsBean;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/15 16:22
 * @desc : 影视详情的viewModel
 * @version: 1.0
 */
public class MovieDetailsViewModel extends BaseViewModel {
    public MovieDetailsBean dataBean;
    public MovieBean movieBean;
    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public MovieBean getDataBean() {
        return movieBean;
    }

    public void setDataBean(MovieBean dataBean) {
        this.movieBean = dataBean;
    }


    public void getMovieDetais(String movieId, final CallBack<MovieDetailsBean> callBack){
        RetrofitClient.getInstance().create(MovieApiService.class)
                .getMovieDetails(movieId)
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
                }).subscribe(new Consumer<Result<MovieDetailsBean>>() {
            @Override
            public void accept(Result<MovieDetailsBean> movieBean) throws Exception {
                if(movieBean.getCode() == 1){
                    dataBean = movieBean.getResult();
                    callBack.success(movieBean.getResult());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissDialog();
                ToastUtils.showShort(throwable.getMessage());
                callBack.fails(throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();

            }
        });

    }
}
