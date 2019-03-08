package com.zzj.open.module_movie.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.zzj.open.base.bean.CallBack;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_movie.BR;
import com.zzj.open.module_movie.R;
import com.zzj.open.module_movie.api.MovieApiService;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.bean.MovieDetailsBean;
import com.zzj.open.module_movie.bean.MovieDetailsItemBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/15 16:22
 * @desc : 影视详情的viewModel
 * @version: 1.0
 */
public class MovieDetailsViewModel extends BaseViewModel {
    public ObservableField<MovieDetailsBean> dataBean = new ObservableField<>();

    public Drawable drawableImg;
    public ObservableField<MovieDetailsItemBean> playUrl = new ObservableField<>();
    public ObservableArrayList<MovieDetailsItemViewModel> observableList = new ObservableArrayList<>();
    public ObservableArrayList<MovieDetailsItemViewModel> playUrls2 = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<MovieDetailsItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.movie_details_seriec_list_item);
    //给RecyclerView添加ItemBinding
    public ItemBinding<MovieDetailsItemViewModel> itemBinding2 = ItemBinding.of(BR.viewModel, R.layout.movie_details_seriec_list_item);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<MovieDetailsItemViewModel> adapter = new BindingRecyclerViewAdapter<>();
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<MovieDetailsItemViewModel> adapter2 = new BindingRecyclerViewAdapter<>();

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(application, R.mipmap.ic_loading);
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
                    dataBean.set(movieBean.getResult());
                    disposePlayUrl();
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

    /**
     * 处理连续剧的集数
     */
    private void disposePlayUrl(){
        List<MovieDetailsBean.PlayUrlsBean> playUrlsBeans = dataBean.get().getPlayUrls();
        List<MovieDetailsBean.PlayUrlsBean> playUrlsBeans2 = dataBean.get().getPlayUrls2();
        if(dataBean.get().getType().equals("连续剧")||dataBean.get().getType().equals("综艺片")||dataBean.get().getType().equals("动漫片")){

            if(playUrlsBeans!=null&&playUrlsBeans.size()>0){
                Collections.sort(playUrlsBeans, new Comparator<MovieDetailsBean.PlayUrlsBean>() {
                    @Override
                    public int compare(MovieDetailsBean.PlayUrlsBean o1, MovieDetailsBean.PlayUrlsBean o2) {
                        Pattern p = Pattern.compile("[^0-9]");
                        Matcher m = p.matcher(o1.getPlayUrl().split("\\$")[0]);
                        String result1 = m.replaceAll("");
                        Matcher m2 = p.matcher(o2.getPlayUrl().split("\\$")[0]);
                        String result2 = m2.replaceAll("");
                        return Integer.valueOf(result1)-Integer.valueOf(result2);
                    }
                });
                Collections.sort(playUrlsBeans2, new Comparator<MovieDetailsBean.PlayUrlsBean>() {
                    @Override
                    public int compare(MovieDetailsBean.PlayUrlsBean o1, MovieDetailsBean.PlayUrlsBean o2) {
                        Pattern p = Pattern.compile("[^0-9]");
                        Matcher m = p.matcher(o1.getPlayUrl().split("\\$")[0]);
                        String result1 = m.replaceAll("");
                        Matcher m2 = p.matcher(o2.getPlayUrl().split("\\$")[0]);
                        String result2 = m2.replaceAll("");
                        return Integer.valueOf(result1)-Integer.valueOf(result2);
                    }
                });

            }

        }

        for(MovieDetailsBean.PlayUrlsBean playUrlsBean : playUrlsBeans){
            String data = playUrlsBean.getPlayUrl();
            if(data.contains("$")){
                MovieDetailsItemBean movieDetailsItemBean = new MovieDetailsItemBean();
                movieDetailsItemBean.setTitle(data.split("\\$")[0]);
                movieDetailsItemBean.setUrl(data.split("\\$")[1]);
                movieDetailsItemBean.setType(1);
                MovieDetailsItemViewModel movieDetailsItemViewModel = new MovieDetailsItemViewModel(this,movieDetailsItemBean);
                observableList.add(movieDetailsItemViewModel);
            }

        }
        for(MovieDetailsBean.PlayUrlsBean playUrlsBean : playUrlsBeans2){
            String data = playUrlsBean.getPlayUrl();
            if(data.contains("$")){
                MovieDetailsItemBean movieDetailsItemBean = new MovieDetailsItemBean();
                movieDetailsItemBean.setTitle(data.split("\\$")[0]);
                movieDetailsItemBean.setUrl(data.split("\\$")[1]);
                movieDetailsItemBean.setType(2);
                MovieDetailsItemViewModel movieDetailsItemViewModel = new MovieDetailsItemViewModel(this,movieDetailsItemBean);
                playUrls2.add(movieDetailsItemViewModel);
            }

        }
    }

}
