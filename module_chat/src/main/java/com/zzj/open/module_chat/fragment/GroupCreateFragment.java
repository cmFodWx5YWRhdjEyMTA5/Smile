package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.base.http.HttpUrl;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.GroupCreateModel;
import com.zzj.open.module_chat.bean.MyFriendModel;
import com.zzj.open.module_chat.databinding.ChatFragmentGroupcreateBinding;
import com.zzj.open.module_chat.utils.Cons;
import com.zzj.open.module_chat.vm.ChatGroupCreateViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupCreateFragment} interface
 * to handle interaction events.
 * Use the {@link GroupCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupCreateFragment extends BaseFragment<ChatFragmentGroupcreateBinding,ChatGroupCreateViewModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BaseQuickAdapter adapter;
    /**
     * 我的联系人
     */
    private List<MyFriendModel> myFriendModels = new ArrayList<>();
    public GroupCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupCreateFragment newInstance(String param1, String param2) {
        GroupCreateFragment fragment = new GroupCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(_mActivity,binding.toolbar,"");
        setHasOptionsMenu(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new BaseQuickAdapter<MyFriendModel,BaseViewHolder>(R.layout.chat_item_group_create_layout) {
            @Override
            protected void convert(BaseViewHolder helper, MyFriendModel item) {
                Glide.with(mContext).load(HttpUrl.IMAGE_URL+item.getFriendFaceImage()).into((ImageView) helper.getView(R.id.iv_portrait));
                helper.setText(R.id.tv_username,item.getFriendNickname());
                helper.addOnClickListener(R.id.iv_check);
                if(item.isSelect()){
                    helper.setImageResource(R.id.iv_check,R.drawable.qmui_icon_checkbox_checked);
                }else {
                    helper.setImageResource(R.id.iv_check,R.drawable.qmui_icon_checkbox_normal);
                }
            }
        };
        binding.recycler.setAdapter(adapter);
        myFriendModels.addAll(viewModel.getMyFriends());
        adapter.setNewData(myFriendModels);
        initListener();
    }

    /**
     * 点击事件
     */
    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.iv_check){
                  MyFriendModel myFriendModel =  myFriendModels.get(position);
                  if(myFriendModel.isSelect()){
                      myFriendModel.setSelect(false);
                  }else {
                      myFriendModel.setSelect(true);
                  }
                  adapter.notifyItemChanged(position);
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.group_create,menu);
        ((Toolbar)binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //提交创建
                if(menuItem.getItemId() == R.id.action_create){
                    //提交
                   submit();
                }
                return false;
            }
        });

    }

    /**
     * 提交创建群
     */
    private void submit() {
        if(binding.editName.getText().toString().equals("")){
            ToastUtils.showShort("群名不能为空");
            return ;
        }
        if(binding.editDesc.getText().toString().equals("")){
            ToastUtils.showShort("群描述不能为空");
            return;
        }
        //保存群组成员id的集合
        Set<String> userIds = new HashSet<>();
        /**
         * 循环选取的联系人，将id添加到创建群的model中
         */
        for(MyFriendModel myFriendModel : myFriendModels){
            if(myFriendModel.isSelect()){
                userIds.add(myFriendModel.getFriendUserId());
            }
        }
        //如果集合为空  说明没有选择人
        if(userIds.size() == 0){
            ToastUtils.showShort("没有选择聊天人员");
            return;
        }
        //添加自己的id到集合中
        userIds.add(com.blankj.utilcode.util.SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
        //创建群model
        GroupCreateModel createModel = new GroupCreateModel();
        createModel.setDesc(binding.editDesc.getText().toString());
        createModel.setName(binding.editName.getText().toString());
        createModel.setUserid(com.blankj.utilcode.util.SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
        createModel.setUsers(userIds);
        //todo 上传群头像
        createModel.setPicture("");
        //创建群
        viewModel.createGroup(createModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_groupcreate;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
