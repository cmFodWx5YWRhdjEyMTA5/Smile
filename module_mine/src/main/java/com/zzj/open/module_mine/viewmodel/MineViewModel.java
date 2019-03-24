package com.zzj.open.module_mine.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UpdateVersion;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.http.ServiceApi;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/14 14:19
 * @desc : 我的  viewModel
 * @version: 1.0
 */
public class MineViewModel extends BaseViewModel {

    public ObservableField<UpdateVersion> updateVersionObservableField = new ObservableField<>();

    public MineViewModel(@NonNull Application application) {
        super(application);
    }


    public void updateVersion(){

        RetrofitClient.getInstance().create(ServiceApi.class)
                .getVersion()
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
                }).subscribe(new Consumer<Result<UpdateVersion>>() {
            @Override
            public void accept(Result<UpdateVersion> result) throws Exception {
                if(result!=null&&result.getCode() == 1){
                    UpdateVersion updateVersion = result.getResult();
                    if(updateVersion.getVersionCode()> AppUtils.getAppVersionCode()){
                        updateVersionObservableField.set(updateVersion);
                    }else {
                        ToastUtils.showShort("已是最新版本");
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();
            }
        });
    }
}
