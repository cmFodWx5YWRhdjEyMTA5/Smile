package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.GroupCard;
import com.zzj.open.module_chat.bean.User;

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
 * @date : 2019/4/13 16:26
 * @desc :
 * @version: 1.0
 */
public class ChatSearchFriendViewModel extends BaseViewModel {

    public ObservableField<User> userObservableField = new ObservableField<>();
    public ObservableField<List<GroupCard>> groupCardObservableField= new ObservableField<>();

    public ChatSearchFriendViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 搜索好友
     * @param text
     */
    public void searchFriend(String text) {

        RetrofitClient.getInstance().create(ApiService.class)
                .searchUser(SPUtils.getInstance().getString("userId"),text)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在搜索...");
                    }
                }).subscribe(new Consumer<Result<User>>() {
            @Override
            public void accept(Result<User> userResult) throws Exception {
                dismissDialog();
                if(userResult.getCode() == 200){
                    userObservableField.set(userResult.getResult());
                }else {
                    ToastUtils.showShort(userResult.getMessage());
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

    /**
     * 搜索群列表
     * @param name
     */
    public void searchGroup(String name) {
        RetrofitClient.getInstance().create(ApiService.class)
                .searchGroup(SPUtils.getInstance().getString("userId"),name)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在搜索...");
                    }
                }).subscribe(new Consumer<Result<List<GroupCard>>>() {
            @Override
            public void accept(Result<List<GroupCard>> userResult) throws Exception {
                dismissDialog();
                if(userResult.getCode() == 200){
                    groupCardObservableField.set(userResult.getResult());
                }else {
                    ToastUtils.showShort(userResult.getMessage());
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
