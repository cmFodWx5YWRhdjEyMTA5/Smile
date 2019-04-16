package com.zzj.open.module_chat.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.FragmentUtils;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.databinding.ChatFragmentSearchfriendBinding;
import com.zzj.open.module_chat.vm.ChatSearchFriendViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/13 16:25
 * @desc : 搜索好友界面
 * @version: 1.0
 */
public class ChatSearchFriendFragment extends BaseFragment<ChatFragmentSearchfriendBinding,ChatSearchFriendViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_searchfriend;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                viewModel.searchFriend(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        viewModel.userObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
//                _mActivity.start();
                _mActivity.start(ChatUserInfoFragment.newInstance(viewModel.userObservableField.get().getId()));
            }
        });
    }
}
