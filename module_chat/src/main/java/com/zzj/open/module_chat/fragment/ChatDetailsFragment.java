package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.databinding.ChatFragmentChatdetailsBinding;
import com.zzj.open.module_chat.databinding.ChatFragmentDetailsBinding;
import com.zzj.open.module_chat.utils.ChatUiHelper;
import com.zzj.open.module_chat.view.RecordButton;

import java.io.File;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/31 16:44
 * @desc : 聊天详情页（仿微信样式）
 * @version: 1.0
 */
public class ChatDetailsFragment extends BaseFragment<ChatFragmentDetailsBinding, BaseViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        initChatUi();
    }

    private void initChatUi(){
        //mBtnAudio
        final ChatUiHelper mUiHelper= ChatUiHelper.with(_mActivity);
        mUiHelper.bindContentLayout(binding.llContent)
                .bindttToSendButton(binding.btnSend)
                .bindEditText(binding.etContent)
                .bindBottomLayout(binding.bottomLayout)
                .bindEmojiLayout((LinearLayout) binding.rlEmotion)
                .bindAddLayout((LinearLayout) binding.llAdd)
                .bindToAddButton(binding.ivAdd)
                .bindToEmojiButton(binding.ivEmo)
                .bindAudioBtn(binding.btnAudio)
                .bindAudioIv(binding.ivAudio)
                .bindEmojiData();
        //底部布局弹出,聊天列表上滑
        binding.rvChatList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    binding.rvChatList.post(new Runnable() {
                        @Override
                        public void run() {
//                            if (mAdapter.getItemCount() > 0) {
//                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
//                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        binding.rvChatList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                binding.etContent.clearFocus();
                binding.ivEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
        //


    }
}
