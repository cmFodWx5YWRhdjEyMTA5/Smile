package com.zzj.open.module_lvji.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.base.BaseModuleInit;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.PicturesCompressor;
import com.zzj.open.base.utils.StreamUtil;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.base.utils.UploadHelper;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.LvJiPublishImageAdapter;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.databinding.LvjiFragmentPublishBinding;
import com.zzj.open.module_lvji.model.EventBean;
import com.zzj.open.module_lvji.model.LvjiPublishModel;
import com.zzj.open.module_lvji.model.LvjiTopicModel;
import com.zzj.open.module_lvji.view.RObject;
import com.zzj.open.module_lvji.viewmodel.LvJiPublishViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/4 20:12
 * @desc : 发布图片fragment
 * @version: 1.0
 */
public class LvJiPublishFragment extends BaseFragment<LvjiFragmentPublishBinding, LvJiPublishViewModel> {

    LvJiPublishImageAdapter imageAdapter;
    private ArrayList<String> imageList = new ArrayList<>();


    /**
     * 位置
     */
    private String location = "";

    /**
     * 城市
     */
    private String city;
    /**
     * 话题
     */
    private String topic = "";

    public static LvJiPublishFragment newInstance(ArrayList<String> imageList) {

        Bundle args = new Bundle();

        LvJiPublishFragment fragment = new LvJiPublishFragment();
        args.putStringArrayList("imageList", imageList);
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
        if (arrayList != null) {
            imageList.addAll(arrayList);
        }
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar, "", true);
        setHasOptionsMenu(true);
//        binding.etContent.setMentionTextColor(R.color.blue_600);
//        binding.etContent.setPattern("@[\\u4e00-\\u9fa5\\w\\-]+");
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 3);
        binding.recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new LvJiPublishImageAdapter(R.layout.lvji_item_publish_image_layout, imageList);
        binding.recyclerView.setAdapter(imageAdapter);

        //获取定位信息
        BaseModuleInit.getInstance().setLocationChangeListener(new BaseModuleInit.LocationChangeListener() {
            @Override
            public void getLocationSuccess(String city,String address) {
//                LogUtils.e("获取的地址----》"+address);
                location = address;
                LvJiPublishFragment.this.city = city;
            }
        });
        //开启定位
        BaseModuleInit.startLocation();

        initListener();
    }
    ConstraintLayout.LayoutParams layoutParams;
    /**
     * 事件监听
     */
    private void initListener() {
        //监听发布成功事件执行关闭页面
        viewModel.aBoolean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                _mActivity.pop();
            }
        });
        /**
         * 话题点击事件
         */
        binding.tvTopic.setOnClickListener(v -> {
           startForResult(new TopicSelectListFragment(),1001);
        });

        /**
         * 监听键盘的高度变化
         */
        KeyboardUtils.registerSoftInputChangedListener(_mActivity, new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                LogUtils.e("onSoftInputChanged---》"+height);
                layoutParams = (ConstraintLayout.LayoutParams) binding.llBottom.getLayoutParams();
                layoutParams.bottomMargin = height;
                if(height>0){
                    binding.llBottom.setVisibility(View.VISIBLE);
                }else {
                    binding.llBottom.setVisibility(View.GONE);
                }

            }
        });
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == 1001&&resultCode == RESULT_OK){
            LvjiTopicModel model = (LvjiTopicModel) data.getSerializable("topic");
            RObject rObject = new RObject();
            rObject.setObjectRule("#");
            rObject.setObjectText(model.getTopicTitle());
            topic = model.getTopicTitle();
            binding.etContent.setObject(rObject);
//            binding.etContent.setSelection(binding.etContent.getText().toString().length());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lvji_menu_publish, menu);
        ((Toolbar) binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //发布按钮事件
                if (menuItem.getItemId() == R.id.action_publish) {
                    showDialog("上传中…");
                    viewModel.uploadingPicture(imageList,location,binding.etContent.getText().toString().trim(),city,topic);
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(_mActivity);
        KeyboardUtils.fixSoftInputLeaks(_mActivity);
    }
}
