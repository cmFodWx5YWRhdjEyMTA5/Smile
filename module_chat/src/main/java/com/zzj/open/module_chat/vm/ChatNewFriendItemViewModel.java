package com.zzj.open.module_chat.vm;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.FriendRequestBean;
import com.zzj.open.module_chat.bean.MyFriendModel;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 新的朋友列表item的viewModel
 * @version: 1.0
 */
public class ChatNewFriendItemViewModel extends ItemViewModel<ChatNewFriendViewModel> {

    public FriendRequestBean friendRequestBean;

    public ChatNewFriendItemViewModel(@NonNull ChatNewFriendViewModel viewModel,FriendRequestBean friendRequestBean) {
        super(viewModel);
        this.friendRequestBean = friendRequestBean;
    }

    /**
     * 接受按钮的点击事件
     */
    public BindingCommand agreeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            operFriendRequest(SPUtils.getInstance().getString("userId"),friendRequestBean.getSendUserId(),1);
        }
    });
    /**
     * 忽略按钮的点击事件
     */
    public BindingCommand rejectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            operFriendRequest(SPUtils.getInstance().getString("userId"),friendRequestBean.getSendUserId(),0);
        }
    });

    /**
     * 操作好友请求的接受和忽略
     * @param acceptUserId 我的id
     * @param sendUserId  发送方id
     * @param operType 1 接受   2 忽略
     */
    public void operFriendRequest(String acceptUserId, String sendUserId,
                                  int operType){
        RetrofitClient.getInstance().create(ApiService.class)
                .operFriendRequest(acceptUserId,sendUserId,operType)
                .compose(RxUtils.bindToLifecycle(this.viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    viewModel.showDialog("正在操作…");
                })
                .subscribe(new Consumer<Result<List<MyFriendModel>>>() {
                    @Override
                    public void accept(Result<List<MyFriendModel>> result) throws Exception {
                        if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                            viewModel.queryFriendRequests(SPUtils.getInstance().getString("userId"));
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
