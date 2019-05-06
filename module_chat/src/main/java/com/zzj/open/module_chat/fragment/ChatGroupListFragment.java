package com.zzj.open.module_chat.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.base.http.HttpUrl;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.PortraitView;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.GroupCard;
import com.zzj.open.module_chat.databinding.ChatFragmentGrouplistBinding;
import com.zzj.open.module_chat.vm.ChatGroupListViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link ChatGroupListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Route(path = RouterFragmentPath.Chat.CHAT_GROUP)
public class ChatGroupListFragment extends BaseFragment<ChatFragmentGrouplistBinding,ChatGroupListViewModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private BaseQuickAdapter adapter;
    public ChatGroupListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatGroupListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatGroupListFragment newInstance(String param1, String param2) {
        ChatGroupListFragment fragment = new ChatGroupListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);
        ToolbarHelper toolbarHelper =new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"群组");
        toolbarHelper.isShowNavigationIcon(false);
        setHasOptionsMenu(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(_mActivity,2));
        viewModel.getGroupList(SPUtils.getInstance().getString("userId"),"");
        adapter = new BaseQuickAdapter<GroupCard,BaseViewHolder>(R.layout.chat_cell_group_list) {

            @Override
            protected void convert(BaseViewHolder helper, GroupCard item) {
                PortraitView portraitView = helper.getView(R.id.im_portrait);
                portraitView.setup(HttpUrl.IMAGE_URL+item.getPicture());
                helper.setText(R.id.txt_name,item.getName());
                helper.setText(R.id.txt_desc,item.getDesc());
            }
        };

        binding.recycler.setAdapter(adapter);
        viewModel.groups.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                adapter.setNewData(viewModel.groups.get());
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GroupCard> groupCardList = viewModel.groups.get();
                GroupCard groupCard = groupCardList.get(position);
                _mActivity.start(ChatFragment.newInstance(groupCard.getId(),groupCard.getName(),groupCard.getPicture(),1));
            }
        });
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
                    _mActivity.start(new GroupCreateFragment());
                }
                return false;
            }
        });
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_grouplist;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


}
