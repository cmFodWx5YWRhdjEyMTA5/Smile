package com.zzj.open.module_lvji.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zzj.open.base.bean.NotifiyEventBean;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.Glide4Engine;
import com.zzj.open.base.utils.ImageUtils;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.adapter.LvjiHomeDynamicAdapter;
import com.zzj.open.module_lvji.databinding.LvjiFragmentHomeBinding;
import com.zzj.open.module_lvji.model.EventBean;
import com.zzj.open.module_lvji.model.LvjiPublishModel;
import com.zzj.open.module_lvji.viewmodel.LvJiHomeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/4 19:10
 * @desc : 游记首页
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Lvji.PAGER_HOME)
public class LvJiHomeFragment extends BaseFragment<LvjiFragmentHomeBinding, LvJiHomeViewModel> {

    public int REQUEST_CODE_CHOOSE = 111;
    public int REQUEST_CODE_SUCCESS= 112;
    private int page = 0;
//    private List<LvjiPublishModel> publishModels = new ArrayList<>();
    private LvjiHomeDynamicAdapter dynamicAdapter;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar,"发现",false);
        setHasOptionsMenu(true);
        viewModel.getPublishList(page);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        dynamicAdapter = new LvjiHomeDynamicAdapter(viewModel.lvjiPublishModels.get());
        binding.recyclerView.setAdapter(dynamicAdapter);

        /**
         * 监听请求的数据变化
         */
        viewModel.lvjiPublishModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (page == 0){
                    dynamicAdapter.setNewData(viewModel.lvjiPublishModels.get());
                }else {
                    dynamicAdapter.addData(viewModel.lvjiPublishModels.get());
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lvji_menu_home,menu);
        ((Toolbar)binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //点击相机功能
                if(menuItem.getItemId() == R.id.action_camera){
                    Matisse.from(LvJiHomeFragment.this)
                            .choose(MimeType.ofAll())
                            .countable(true)
                            .maxSelectable(9)
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true,""))
//                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new Glide4Engine())
                            .forResult(REQUEST_CODE_CHOOSE);
                }
                return false;
            }
        });
    }

    /**
     * 图片地址集合
     */
    List<String> mSelected;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data);
            if(mSelected!=null&&mSelected.size()!=0){
//                String imageBase64 = ImageUtils.imageToBase64(mSelected.get(0));
                _mActivity.start(LvJiPublishFragment.newInstance((ArrayList<String>) mSelected));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(EventBean eventBean) {
        if(eventBean.getMsg().equals("上传成功")){
            page = 0;
            viewModel.getPublishList(page);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
