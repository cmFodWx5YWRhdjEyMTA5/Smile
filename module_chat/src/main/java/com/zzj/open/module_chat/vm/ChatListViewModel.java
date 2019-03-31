package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.BR;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/24 10:30
 * @desc : 聊天列表的viewModel
 * @version: 1.0
 */
public class ChatListViewModel extends BaseViewModel {

    public ObservableBoolean aBoolean = new ObservableBoolean(false);
    /**
     * 封装一个界面发生改变的观察者
     */
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        /**
         * 下拉刷新完成
         */
        public ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        /**
         * 上拉加载完成
         */
        public ObservableBoolean finishLoadmore = new ObservableBoolean(false);
    }

    public ChatListViewModel(@NonNull Application application) {
        super(application);

    }

    public ObservableArrayList<ChatListItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<ChatListItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.chat_chatlist_item);

    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    public final BindingRecyclerViewAdapter<ChatListItemViewModel> adapter = new BindingRecyclerViewAdapter<>();



    public void initData(){

        ChatListItemViewModel chatListItemViewModel = new ChatListItemViewModel(ChatListViewModel.this);
        observableList.add(chatListItemViewModel);
        observableList.add(chatListItemViewModel);
        observableList.add(chatListItemViewModel);
        observableList.add(chatListItemViewModel);
        observableList.add(chatListItemViewModel);
    }

}
