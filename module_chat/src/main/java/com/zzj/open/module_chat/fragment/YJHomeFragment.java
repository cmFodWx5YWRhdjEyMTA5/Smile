package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.activity.ChatActivity;
import com.zzj.open.module_chat.databinding.ChatYjhomeFragmentBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/31 16:40
 * @desc : 游记首页
 * @version: 1.0
 */

public class YJHomeFragment extends BaseFragment<ChatYjhomeFragmentBinding, BaseViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_yjhome_fragment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.tvTest.setOnClickListener(v -> {
//            _mActivity.start(new ChatDetailsFragment());
            ChatActivity.start(_mActivity);
        });
    }
}
