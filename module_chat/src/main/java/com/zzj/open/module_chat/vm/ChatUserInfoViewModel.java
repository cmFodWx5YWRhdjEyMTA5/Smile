package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.User;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/15 9:17
 * @desc :
 * @version: 1.0
 */
public class ChatUserInfoViewModel extends BaseViewModel {

    public ObservableField<User> user = new ObservableField<>();
    public Drawable drawableImg;
    public ChatUserInfoViewModel(@NonNull Application application) {
        super(application);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(getApplication(), R.mipmap.ic_loading);
    }

    /**
     * 添加通讯录的点击事件
     */
    public BindingCommand addLinkClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            addFriendRequest(SPUtils.getInstance().getString("userId"),user.get().getUsername());
        }
    });

    /**
     * 请求个人信息
     * @param userId 用户id
     */
    public void requestUserInfo(String userId) {
        RetrofitClient.getInstance().create(ApiService.class)
                .getUserInfo(userId)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在查询...");
                    }
                }).subscribe(new Consumer<Result<User>>() {
            @Override
            public void accept(Result<User> result) throws Exception {
                if(result.getCode() == 200){
                    user.set(result.getResult());
                }else {
                    ToastUtils.showShort(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();
            }
        });
    }

    /**
     * 添加好友请求
     * @param myUserId 我的id
     * @param friendName 添加的用户名
     */
    public void addFriendRequest(String myUserId,String friendName){
        RetrofitClient.getInstance().create(ApiService.class)
                .addFriendRequest(myUserId,friendName)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    showDialog("正在添加...");
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                            ToastUtils.showShort("发送添加好友成功");
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }
                    }
                }, throwable -> {
                    ToastUtils.showShort(((Throwable)throwable).getMessage());
                }, () -> {
                    dismissDialog();
                });
    }
}
