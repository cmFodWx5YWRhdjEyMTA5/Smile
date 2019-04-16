package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.FriendRequestBean;

import java.util.List;

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
 * @desc : 新朋友的viewModel
 * @version: 1.0
 */
public class ChatNewFriendViewModel extends BaseViewModel {


    public ChatNewFriendViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableArrayList<ChatNewFriendItemViewModel> observableList = new ObservableArrayList<>();

    //给RecyclerView添加ItemBinding
    public ItemBinding<ChatNewFriendItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.chat_item_newfriend_list);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<ChatNewFriendItemViewModel> adapter = new BindingRecyclerViewAdapter<>();


    /**
     * 获取好友请求列表
     * @param userId
     */
    public void queryFriendRequests(String userId){
        observableList.clear();
        RetrofitClient.getInstance().create(ApiService.class)
                .queryFriendRequests(userId)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    showDialog("正在查询…");
                })
                .subscribe(new Consumer<Result<List<FriendRequestBean>>>() {
                    @Override
                    public void accept(Result<List<FriendRequestBean>> result) throws Exception {
                        if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                            List<FriendRequestBean> friendRequestBeans = result.getResult();
                            if(friendRequestBeans!=null&&friendRequestBeans.size()>0){
                                for(FriendRequestBean friendRequestBean : friendRequestBeans){
                                    observableList.add(new ChatNewFriendItemViewModel(ChatNewFriendViewModel.this,friendRequestBean));
                                }
                            }
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
                        dismissDialog();
                    }
                });

    }
}
