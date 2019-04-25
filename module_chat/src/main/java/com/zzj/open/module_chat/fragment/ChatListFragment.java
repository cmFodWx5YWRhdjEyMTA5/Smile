package com.zzj.open.module_chat.fragment;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dhh.websocket.RxWebSocket;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.adapter.ChatListAdapter;
import com.zzj.open.module_chat.bean.ChatListModel;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.DataContent;
import com.zzj.open.module_chat.databinding.ChatFragmentChatlistBinding;
import com.zzj.open.module_chat.service.ChatMessageService;
import com.zzj.open.module_chat.vm.ChatListViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BR;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/24 10:27
 * @desc :  聊天列表
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Chat.CHAT_HOME)
public class ChatListFragment extends BaseFragment<ChatFragmentChatlistBinding,ChatListViewModel> {


    private ChatListAdapter chatListAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_chatlist;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        _mActivity.startService(new Intent(_mActivity,ChatMessageService.class));
        setSwipeBackEnable(false);
        ToolbarHelper toolbarHelper =new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"消息");
        toolbarHelper.isShowNavigationIcon(false);
        setHasOptionsMenu(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        chatListAdapter = new ChatListAdapter(R.layout.chat_chatlist_item,viewModel.chatListModels);
        binding.recyclerView.setAdapter(chatListAdapter);
        viewModel.getUnReadMsgList();
        viewModel.initData();
        chatListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatListModel chatListModel = viewModel.chatListModels.get(position);
                _mActivity.start(ChatFragment.newInstance(chatListModel.getChatUserId(),chatListModel.getChatUserName(),chatListModel.getChatFaceImage()));
            }
        });
        viewModel.isUpdateList.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                chatListAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        viewModel.initData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        ((Toolbar)binding.toolbar).inflateMenu(R.menu.chat_menu);
        inflater.inflate(R.menu.chat_menu,menu);
        ((Toolbar)binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_contact){
                    _mActivity.start(new ChatSearchFriendFragment());
                }
                return false;
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(DataContent dataContent) {

        //接收到聊天消息
        viewModel.initData();
        if(dataContent.getAction() == 1){
            SPUtils.getInstance().put("websocket",1);
            viewModel.getUnReadMsgList();
        }
    }
}
