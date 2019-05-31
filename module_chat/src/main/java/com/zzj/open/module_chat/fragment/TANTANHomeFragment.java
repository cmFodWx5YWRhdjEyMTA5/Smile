package com.zzj.open.module_chat.fragment;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UsersVO;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.adapter.ChatHomeSlideCardAdapter;
import com.zzj.open.module_chat.bean.SlideCardBean;
import com.zzj.open.module_chat.databinding.ChatFragmentHomeBinding;
import com.zzj.open.module_chat.service.ChatMessageService;
import com.zzj.open.module_chat.slidecard.CardConfig;
import com.zzj.open.module_chat.slidecard.CardItemTouchHelperCallback;
import com.zzj.open.module_chat.slidecard.CardLayoutManager;
import com.zzj.open.module_chat.slidecard.OnSwipeListener;
import com.zzj.open.module_chat.vm.ChatHomeViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 0:07
 * @desc : 首页
 * @version: 1.0
 */

public class TANTANHomeFragment extends BaseFragment<ChatFragmentHomeBinding, ChatHomeViewModel> {

    private ChatHomeSlideCardAdapter slideCardAdapter;

    private List<UsersVO> slideCardBeans = new ArrayList<>();

    private UsersVO usersVO = null;
    private CardItemTouchHelperCallback cardCallback;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar, "首页", false);
        setSwipeBackEnable(false);
        _mActivity.startService(new Intent(_mActivity, ChatMessageService.class));
        initAdapter();
        viewModel.listUserCard(SPUtils.getInstance().getString("userId"), "");
        viewModel.listObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                slideCardBeans.addAll(viewModel.listObservableField.get());
                slideCardAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initAdapter() {
        slideCardAdapter = new ChatHomeSlideCardAdapter(R.layout.chat_item_home_slide_card_layout, slideCardBeans);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        slideCardAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        binding.recyclerView.setAdapter(slideCardAdapter);
        cardCallback = new CardItemTouchHelperCallback(binding.recyclerView.getAdapter(), slideCardBeans);
        cardCallback.setOnSwipedListener(new OnSwipeListener<UsersVO>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                MyAdapter.MyViewHolder myHolder = (MyAdapter.MyViewHolder) viewHolder;
//                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, UsersVO slideCardBean, int direction) {
                Toast.makeText(_mActivity, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(_mActivity, "data clear", Toast.LENGTH_SHORT).show();

            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(binding.recyclerView, touchHelper);
        binding.recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(binding.recyclerView);
        /**
         * 发起聊天
         */
        binding.fbChat.setOnClickListener(v -> {
            if(slideCardBeans.size() == 0){
                return;
            }
            usersVO = slideCardBeans.get(0);
            LogUtils.e("haha" + usersVO.getId());
            _mActivity.start(ChatFragment.newInstance(usersVO.getId(), usersVO.getNickname(), usersVO.getFaceImageBig(), 0));
        });
        /**
         * 不喜欢
         */
        binding.fbDislike.setOnClickListener(v -> {
            if(slideCardBeans.size() == 0){
                return;
            }

            ToastUtils.showShort("fbDislike-----");
        });
        /**
         * 喜欢
         */
        binding.fbLike.setOnClickListener(v -> {
            if(slideCardBeans.size() == 0){
                return;
            }

            ToastUtils.showShort("fbLike-----");
        });
        /**
         * 收藏
         */
        binding.fbCollect.setOnClickListener(v -> {
            if(slideCardBeans.size() == 0){
                return;
            }
            ToastUtils.showShort("fbCollect-----");
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(Result result) {
        //接收到退出通知，清理数据库数据，跳转登录页
        if (result != null && result.getCode() == 404) {
            ChatModuleInit.getDaoSession().getChatListModelDao().deleteAll();
            ChatModuleInit.getDaoSession().getChatMessageModelDao().deleteAll();
            _mActivity.stopService(new Intent(_mActivity, ChatMessageService.class));
            BaseFragment fragment = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Mine.MINE_LOGIN).navigation();
            _mActivity.replaceFragment(fragment, false);
        }
    }

}
