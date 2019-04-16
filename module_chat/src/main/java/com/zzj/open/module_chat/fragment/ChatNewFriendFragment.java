package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.databinding.ChatFragmentNewfriendBinding;
import com.zzj.open.module_chat.vm.ChatNewFriendViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 新的朋友界面
 * @version: 1.0
 */
public class ChatNewFriendFragment extends BaseFragment<ChatFragmentNewfriendBinding,ChatNewFriendViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_newfriend;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(_mActivity,(Toolbar)binding.toolbar,"新的朋友");
        viewModel.queryFriendRequests(SPUtils.getInstance().getString("userId"));
    }
}
