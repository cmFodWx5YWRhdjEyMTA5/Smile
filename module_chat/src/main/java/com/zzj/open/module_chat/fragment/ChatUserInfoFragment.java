package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.User;
import com.zzj.open.module_chat.databinding.ChatFragmentUserinfoBinding;
import com.zzj.open.module_chat.vm.ChatUserInfoViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/13 16:59
 * @desc : 聊天用户的信息界面
 * @version: 1.0
 */
public class ChatUserInfoFragment extends BaseFragment<ChatFragmentUserinfoBinding,ChatUserInfoViewModel> {

    private String userId;

    public static ChatUserInfoFragment newInstance(String userId) {

        Bundle args = new Bundle();
        ChatUserInfoFragment fragment = new ChatUserInfoFragment();
        args.putString("userId",userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_userinfo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"用户信息");
        userId = getArguments().getString("userId");
        viewModel.requestUserInfo(userId);
    }
}
