package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.GroupCard;

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
 * @date : 2019/5/6 12:28
 * @desc :
 * @version: 1.0
 */
public class ChatGroupListViewModel extends BaseViewModel {

    public ObservableField<List<GroupCard>> groups = new ObservableField<>();
    public ChatGroupListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 拉取群列表
     */
    public void getGroupList(String userId,String dateStr){
        RetrofitClient.getInstance().create(ApiService.class)
                .groupList(userId, dateStr)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中…");
                    }
                })
                .subscribe(new Consumer<Result<List<GroupCard>>>() {
                    @Override
                    public void accept(Result<List<GroupCard>> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            groups.set(result.getResult());
                            ChatModuleInit.getDaoSession().getGroupCardDao().insertOrReplaceInTx(result.getResult());
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                    }
                });
    }
}
