package com.zzj.open.module_lvji.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.LvJiPublishImageAdapter;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.databinding.LvjiFragmentPublishBinding;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/4 20:12
 * @desc : 发布图片fragment
 * @version: 1.0
 */
public class LvJiPublishFragment extends BaseFragment<LvjiFragmentPublishBinding, BaseViewModel> {

    LvJiPublishImageAdapter imageAdapter;
    private ArrayList<String> imageList = new ArrayList<>();
    public static LvJiPublishFragment newInstance(ArrayList<String> imageList) {

        Bundle args = new Bundle();

        LvJiPublishFragment fragment = new LvJiPublishFragment();
        args.putStringArrayList("imageList",imageList);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_publish;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        ArrayList<String> arrayList = getArguments().getStringArrayList("imageList");
        if(arrayList!=null){
            imageList.addAll(arrayList);
        }
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar,"",true);
        setHasOptionsMenu(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(_mActivity,4));
        imageAdapter = new LvJiPublishImageAdapter(R.layout.lvji_item_publish_image_layout,imageList);
        binding.recyclerView.setAdapter(imageAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lvji_menu_publish,menu);
        ((Toolbar) binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //发布按钮事件
                if(menuItem.getItemId() == R.id.action_publish){


                }                return false;
            }
        });
    }
}
