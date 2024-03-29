package com.zzj.open.module_lvji.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.Glide4Engine;
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

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/4 19:10
 * @desc : 游记首页
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Lvji.PAGER_HOME)
public class LvJiHomeFragment extends BaseFragment<LvjiFragmentHomeBinding, LvJiHomeViewModel> {

    public static int REQUEST_CODE_CHOOSE = 111;
    public static int REQUEST_CODE_SUCCESS = 112;
    private int page = 0;
    //    private List<LvjiPublishModel> publishModels = new ArrayList<>();
    private LvjiHomeDynamicAdapter dynamicAdapter;

    private LvjiPublishModel publishModel;
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
        setSwipeBackEnable(false);
        EventBus.getDefault().register(this);
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar, "动态", false);
        setHasOptionsMenu(true);
        viewModel.getPublishList(page);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        dynamicAdapter = new LvjiHomeDynamicAdapter(viewModel.lvjiPublishModels.get());
        binding.recyclerView.setAdapter(dynamicAdapter);
        initListener();
        initPop();
    }

    /**
     * 事件监听
     */
    private void initListener() {
        /**
         * 监听请求的数据变化
         */
        viewModel.lvjiPublishModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                if (page == 0) {
                    dynamicAdapter.setNewData(viewModel.lvjiPublishModels.get());
                } else {
                    dynamicAdapter.addData(viewModel.lvjiPublishModels.get());
                }
            }
        });

        /**
         * item中的控件点击事件
         */
        dynamicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //更多按钮的点击事件   弹窗
                if(view.getId() == R.id.iv_more){
                    publishModel = (LvjiPublishModel) adapter.getData().get(position);
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    if(location[1]< ScreenUtils.getScreenHeight()- ConvertUtils.dp2px(120)){
                        popup.setPreferredDirection(QMUIPopup.DIRECTION_BOTTOM);
                    }else {
                        popup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
                    }
                    LogUtils.e("iv_more--->y--->"+location[1]);
                    popup.show(view);
                }
            }
        });
        /**
         * item点击事件
         */
        dynamicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                viewModel.getPublishList(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                viewModel.getPublishList(page);
            }
        });

        /**
         * 网络请求结束监听
         */
        viewModel.isRefresh.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.refresh.finishRefresh();
                binding.refresh.finishLoadMore();
            }
        });
    }

    private QMUIPopup popup;
    /**
     * 显示更多弹窗
     */
    private void initPop() {
        if (popup == null) {
            popup = new QMUIPopup(_mActivity);
            View view = LayoutInflater.from(_mActivity).inflate(R.layout.lvji_pop_dynamic_more_layout,null);
            TextView tvSendMessage = view.findViewById(R.id.tv_send_message);
            tvSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  try {
//                      String className = "com.zzj.open.module_chat.fragment.ChatFragment";
//                      ReflectUtils reflectUtils = ReflectUtils.reflect(className);
//                      reflectUtils.method("instance", publishModel.getUserId(),publishModel.getUserName(),publishModel.getFaceImage(),0);
                      BaseFragment fragment =(BaseFragment) ARouter.getInstance()
                              .build(RouterFragmentPath.Msg.PAGER_MSG_DETAILS)
                              .withString("chatUserId", publishModel.getUserId())
                              .withString("chatUsername", publishModel.getUserId())
                              .withString("chatFaceImage", publishModel.getUserId())
                              .withInt("chatType", 0)
                              .navigation();
                      _mActivity.start(fragment);
                      popup.dismiss();
                  }catch (Exception e){
                      e.printStackTrace();

                  }
                }
            });
            popup.setContentView(view);

            popup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);



        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((Toolbar) binding.toolbar).getMenu().clear();
        ((Toolbar) binding.toolbar).inflateMenu(R.menu.lvji_menu_home);
        ((Toolbar) binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //点击相机功能
                if (menuItem.getItemId() == R.id.action_camera) {
                    Matisse.from(LvJiHomeFragment.this)
                            .choose(MimeType.ofAll())
                            .countable(true)
                            .maxSelectable(9)
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true, ""))
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
            if (mSelected != null && mSelected.size() != 0) {
//                String imageBase64 = ImageUtils.imageToBase64(mSelected.get(0));
                _mActivity.start(LvJiPublishFragment.newInstance((ArrayList<String>) mSelected),SINGLETOP);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(EventBean eventBean) {
        if (eventBean.getMsg().equals("上传成功")) {
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
