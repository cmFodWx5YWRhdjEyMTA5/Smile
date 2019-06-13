package com.zzj.open.module_lvji.viewmodel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.model.LvjiPublishModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/12 18:34
 * @desc : 游记动态的viewModel
 * @version: 1.0
 */
public class LvJiHomeViewModel extends BaseViewModel {

    public ObservableField<List<LvjiPublishModel>> lvjiPublishModels = new ObservableField<>();
    public LvJiHomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPublishList(int page){
        RetrofitClient.getInstance().create(ApiService.class)
                .getPublishList(page,10)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<List<LvjiPublishModel>>>() {
                    @Override
                    public void accept(Result<List<LvjiPublishModel>> result) throws Exception {
                        if(result.getCode() == 200){
                            lvjiPublishModels.set(result.getResult());
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });
    }
}
