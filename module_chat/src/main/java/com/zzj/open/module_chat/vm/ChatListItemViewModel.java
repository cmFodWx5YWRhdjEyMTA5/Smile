package com.zzj.open.module_chat.vm;

import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/30 18:52
 * @desc :
 * @version: 1.0
 */
public class ChatListItemViewModel extends ItemViewModel<ChatListViewModel> {

    public ChatListItemViewModel(@NonNull ChatListViewModel viewModel) {
        super(viewModel);

    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //这里可以通过一个标识,做出判断，已达到跳入不能界面的逻辑
            viewModel.aBoolean.set(!viewModel.aBoolean.get());

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
