package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.adapter.ChatContactAdapter;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.MyFriendBean;
import com.zzj.open.module_chat.bean.User;
import com.zzj.open.module_chat.databinding.ChatFragmentContactBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/13 14:46
 * @desc : 联系人
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Chat.CHAT_CONTACT)
public class ChatContactFragment extends BaseFragment<ChatFragmentContactBinding,BaseViewModel> {


    private ChatContactAdapter chatContactAdapter;
    private List<MyFriendBean> users = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_contact;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);
        ToolbarHelper toolbarHelper =new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"联系人");
        toolbarHelper.isShowNavigationIcon(false);
        setHasOptionsMenu(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(_mActivity));
        chatContactAdapter = new ChatContactAdapter(R.layout.chat_item_contact_list,users);
        binding.rv.setAdapter(chatContactAdapter);
        newFriendLayout();
        chatContactAdapter.notifyDataSetChanged();
        getMyFriends(SPUtils.getInstance().getString("userId"));

        chatContactAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyFriendBean myFriendBean = users.get(position);
                _mActivity.start(ChatFragment.newInstance(myFriendBean.getFriendUserId(),myFriendBean.getFriendUsername(),myFriendBean.getFriendFaceImage()));
            }
        });
    }


    /**
     * 请求好友列表
     * @param userId
     */
    private void getMyFriends(String userId){
        RetrofitClient.getInstance().create(ApiService.class)
                .myFriends(userId)
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    showDialog("请稍等…");
                })
                .subscribe(new Consumer<Result<List<MyFriendBean>>>() {
                    @Override
                    public void accept(Result<List<MyFriendBean>> result) throws Exception {
                        if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                            users.addAll(result.getResult());
                            chatContactAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                    }
                });

    }

    /**
     * 联系人中新的朋友的布局及点击事件
     */
    private void newFriendLayout(){
        View newFriendView = LayoutInflater.from(_mActivity).inflate(R.layout.chat_item_contact_list,null);
        TextView textView = newFriendView.findViewById(R.id.tv_username);
        textView.setText("新的朋友");
        chatContactAdapter.addHeaderView(newFriendView);
        newFriendView.setOnClickListener(v -> {
            _mActivity.start(new ChatNewFriendFragment());
        });
    }
}
