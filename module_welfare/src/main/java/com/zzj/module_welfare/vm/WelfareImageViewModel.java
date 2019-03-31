package com.zzj.module_welfare.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.zzj.module_welfare.BR;
import com.zzj.module_welfare.R;
import com.zzj.module_welfare.adapter.WelfareImageAdapter;
import com.zzj.module_welfare.api.WelfareServiceApi;
import com.zzj.module_welfare.bean.GanKImageBean;
import com.zzj.open.base.http.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 19:13
 * @desc : 图片ViewModel
 * @version: 1.0
 */
public class WelfareImageViewModel extends BaseViewModel {

    public ObservableField<GanKImageBean.ResultsBean> itemBean = new ObservableField<>();
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
    public WelfareImageViewModel(@NonNull Application application) {
        super(application);
    }
    public ObservableArrayList<WelfareImageItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<WelfareImageItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.welfare_item_images);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final WelfareImageAdapter adapter = new WelfareImageAdapter();



    public void requestNetWork(final int page) {
        RetrofitClient.getInstance().create(WelfareServiceApi.class)
                .getWelfareImageList(page)
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
                }).subscribe(new Consumer<GanKImageBean>() {
            @Override
            public void accept(GanKImageBean ganKImageBean) throws Exception {
                uc.finishLoadmore.set(!uc.finishLoadmore.get());
                if(!ganKImageBean.isError()){

                    if(page == 0){
                        observableList.clear();
                    }
                    for (GanKImageBean.ResultsBean dataBean : ganKImageBean.getResults()) {

                        WelfareImageItemViewModel itemViewModel = new WelfareImageItemViewModel(WelfareImageViewModel.this, dataBean);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        itemBean = null;
    }
}
