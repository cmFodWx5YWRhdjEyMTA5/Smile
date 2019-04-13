package com.zzj.open.module_chat.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.databinding.ChatFragmentChatlistBinding;
import com.zzj.open.module_chat.vm.ChatListViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/24 10:27
 * @desc :  聊天列表
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Chat.CHAT_HOME)
public class ChatListFragment extends BaseFragment<ChatFragmentChatlistBinding,ChatListViewModel> {


//    String url = "ws://121.40.165.18:8800";
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
        setSwipeBackEnable(false);
        ToolbarHelper toolbarHelper =new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"消息");
        toolbarHelper.isShowNavigationIcon(false);
        setHasOptionsMenu(true);
        //用WebSocket的引用直接发
        viewModel.initData();

        viewModel.aBoolean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                _mActivity.start(new ChatFragment());
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        ((Toolbar)binding.toolbar).inflateMenu(R.menu.chat_menu);
        inflater.inflate(R.menu.chat_menu,menu);

    }
}
