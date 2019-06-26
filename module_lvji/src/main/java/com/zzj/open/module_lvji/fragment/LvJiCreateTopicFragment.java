package com.zzj.open.module_lvji.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zzj.open.base.base.BaseModuleInit;
import com.zzj.open.base.bean.CallBack;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.Glide4Engine;
import com.zzj.open.base.utils.ImageUtils;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.databinding.LvjiFragmentCreateTopBinding;
import com.zzj.open.module_lvji.model.EventBean;
import com.zzj.open.module_lvji.model.LvjiTopicModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.zzj.open.module_lvji.fragment.LvJiHomeFragment.REQUEST_CODE_CHOOSE;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/22
 * @desc : 创建话题
 * @version: 1.0
 */
public class LvJiCreateTopicFragment extends BaseFragment<LvjiFragmentCreateTopBinding, BaseViewModel> {

    private String location;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_create_top;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar,"",true);
        setHasOptionsMenu(true);


        /**
         * 头像
         */
        binding.ivTopicPicture.setOnClickListener(view -> {

            Matisse.from(LvJiCreateTopicFragment.this)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(1)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, ""))
//                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(REQUEST_CODE_CHOOSE);

        });

        //获取定位信息
        BaseModuleInit.getInstance().setLocationChangeListener(new BaseModuleInit.LocationChangeListener() {
            @Override
            public void getLocationSuccess(String city,String address) {
//                LogUtils.e("获取的地址----》"+address);
                location = address;
            }
        });
        //开启定位
        BaseModuleInit.startLocation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((Toolbar)binding.toolbar).inflateMenu(R.menu.lvji_menu_publish);
        ((Toolbar)binding.toolbar).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String topicTitle = binding.etTopicTitle.getText().toString().trim();
                String topicDesc = binding.etTopicDesc.getText().toString().trim();
                if(mSelected == null){
                    ToastUtils.showShort("话题头像不能为空");
                    return false;
                }
                if(topicTitle.equals("")){
                    ToastUtils.showShort("话题标题不能为空");
                    return false;
                }
                if(topicDesc.equals("")){
                    ToastUtils.showShort("话题描述不能为空");
                    return false;
                }


                LvjiTopicModel topicModel = new LvjiTopicModel();
                topicModel.setUserId(SPUtils.getInstance().getString("userId"));
                topicModel.setTopicContent(topicDesc);
                topicModel.setTopicTitle(topicTitle);
                topicModel.setTopicLocation(location);
                topicModel.setTopicKind("user");
                //todo:  话题类型id
                topicModel.setTypeId("sy23syui679");
                showDialog("正在创建...");
                ImageUtils.uploadImage(mSelected.get(0), viewModel, new CallBack<String>() {
                    @Override
                    public void success(String result) {
                        topicModel.setTopicPicture(result);
                        createTopic(topicModel);
                    }

                    @Override
                    public void fails(String error) {
                        dismissDialog();
                    }
                });
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
                Glide.with(_mActivity).load(mSelected.get(0)).apply(new RequestOptions().placeholder(R.mipmap.bg_src_morning)).into(binding.ivTopicPicture);
            }
        }
    }


    /**
     * 创建话题
     */
    private void createTopic(LvjiTopicModel topicModel){

        RetrofitClient.getInstance().create(ApiService.class)
                .createTopic(topicModel)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).subscribe(new Consumer<Result<LvjiTopicModel>>() {
            @Override
            public void accept(Result<LvjiTopicModel> o) throws Exception {
                dismissDialog();
                if(o!=null&&o.getCode() == 200){
                    com.blankj.utilcode.util.ToastUtils.showShort("创建成功");
                    pop();
                    EventBus.getDefault().post(new EventBean("LvJiCreateTopicFragment"));
                }else {

                    com.blankj.utilcode.util.ToastUtils.showShort("创建失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissDialog();
                com.blankj.utilcode.util.ToastUtils.showShort("创建失败");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();
            }
        });
    }
}
