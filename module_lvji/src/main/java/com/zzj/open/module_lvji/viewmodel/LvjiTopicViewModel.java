package com.zzj.open.module_lvji.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.model.LvjiTopicModel;
import com.zzj.open.module_lvji.model.LvjiTopicTypeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/21
 * @desc : 话题界面的ViewModel
 * @version: 1.0
 */
public class LvjiTopicViewModel extends BaseViewModel {

    public ObservableField<List<LvjiTopicModel>> topicModels = new ObservableField<>();
    public ObservableField<List<LvjiTopicModel>> topicBannerModels = new ObservableField<>();
    public ObservableField<List<LvjiTopicTypeModel>> topicTypeModels = new ObservableField<>();

    public LvjiTopicViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取话题列表
     */
    public void getTopicList(String topicKind){
        RetrofitClient.getInstance().create(ApiService.class)
                .getTopicList(topicKind)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<List<LvjiTopicModel>>>() {
                    @Override
                    public void accept(Result<List<LvjiTopicModel>> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            if(topicKind.equals("sys")){
                                topicBannerModels.set(result.getResult());
                            }else {
                                topicModels.set(result.getResult());
                            }
                        }else {

                            ToastUtils.showShort(result.getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("服务器错误");
                    }
                });
    }
    /**
     * 获取话题分类列表
     */
    public void getTopicTypeList(){
        RetrofitClient.getInstance().create(ApiService.class)
                .getTopicTypeList()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<List<LvjiTopicTypeModel>>>() {
                    @Override
                    public void accept(Result<List<LvjiTopicTypeModel>> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            topicTypeModels.set(result.getResult());
                        }else {

                            ToastUtils.showShort(result.getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("服务器错误");
                    }
                });
    }
}
