package com.zzj.open.module_chat.vm;

import android.support.annotation.NonNull;
import android.view.View;

import com.zzj.open.module_chat.bean.ChatListModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc :
 * @version: 1.0
 */
public class ChatListItemViewModel extends ItemViewModel<ChatListViewModel> {
    public ChatListModel chatListModel;

    public ChatListItemViewModel(@NonNull ChatListViewModel viewModel,ChatListModel chatListModel) {
        super(viewModel);
        this.chatListModel = chatListModel;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {


        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
//            viewModel.deleteItemLiveData.setValue(NetWorkItemViewModel.this);
        }
    });

}
