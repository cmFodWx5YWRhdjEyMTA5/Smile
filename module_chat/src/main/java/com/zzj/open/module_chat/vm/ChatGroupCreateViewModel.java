package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.GroupCard;
import com.zzj.open.module_chat.bean.GroupCreateModel;
import com.zzj.open.module_chat.bean.MyFriendModel;

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
 * @date : 2019/5/5 12:32
 * @desc : 创建群组的viewModel
 * @version: 1.0
 */
public class ChatGroupCreateViewModel extends BaseViewModel {

    public ChatGroupCreateViewModel(@NonNull Application application) {
        super(application);

    }

    /**
     * 获取好友列表
     */
    public List<MyFriendModel> getMyFriends() {
        return ChatModuleInit.getDaoSession().getMyFriendModelDao().loadAll();
    }

    /**
     * 创建群组
     * @param createModel
     */
    public void createGroup(GroupCreateModel createModel) {
        RetrofitClient.getInstance().create(ApiService.class)
                .createGroup(createModel)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("创建中…");
                    }
                })
                .subscribe(new Consumer<Result<GroupCard>>() {
                    @Override
                    public void accept(Result<GroupCard> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            ToastUtils.showShort("创建成功");
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
