package com.zzj.open.module_lvji.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observable;
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
public class LvJiPublishFragment extends BaseFragment<LvjiFragmentPublishBinding, BaseViewModel> {

    LvJiPublishImageAdapter imageAdapter;
    private ArrayList<String> imageList = new ArrayList<>();
    /**
     * 上传图片的url
     */
    private ArrayList<String> imageUrlList = new ArrayList<>();
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
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 3);
        binding.recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new LvJiPublishImageAdapter(R.layout.lvji_item_publish_image_layout, imageList);
        binding.recyclerView.setAdapter(imageAdapter);

        //获取定位信息
        BaseModuleInit.getInstance().setLocationChangeListener(new BaseModuleInit.LocationChangeListener() {
            @Override
            public void getLocationSuccess(String address) {
                LogUtils.e("获取的地址----》"+address);
                location = address;
            }
        });
        //开启定位
        BaseModuleInit.startLocation();
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
                    Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                            LogUtils.e("ObservableEmitter--->" + Thread.currentThread().getName());
                            imageUrlList.clear();
                            for (String path : imageList) {
                                //执行上传操作
                                // 进行压缩
                                String cacheDir = BaseModuleInit.application.getCacheDir().getAbsolutePath();
                                final String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());
                                // 压缩工具类
                                if (PicturesCompressor.compressImage(path, tempFile,
                                        UploadHelper.MAX_UPLOAD_IMAGE_LENGTH)) {

                                    // 上传
                                    String ossPath = UploadHelper.uploadImage(tempFile);
                                    // 清理缓存
                                    StreamUtil.delete(tempFile);
                                    emitter.onNext(ossPath);
                                }
                            }
                            emitter.onComplete();
                        }
                    }).subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                private Disposable mDisposable;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    mDisposable = d;
                                }

                                @Override
                                public void onNext(String s) {
                                    LogUtils.e("onNext--->" + s);
                                    imageUrlList.add(s);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    mDisposable.dispose();
                                    dismissDialog();
                                    ToastUtils.showShort("上传失败");
                                    LogUtils.e("onError---->" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    publish();


                                }
                            });

                }
                return false;
            }
        });
    }

    /**
     * 位置
     */
    private String location = "";


    /**
     * 上传分享图片
     */
    private void publish(){
        RetrofitClient.getInstance().create(ApiService.class)
                .publish(SPUtils.getInstance().getString("userId"),location,imageUrlList.toString(),binding.etContent.getText().toString().trim())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<LvjiPublishModel>>() {
                    @Override
                    public void accept(Result<LvjiPublishModel> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            ToastUtils.showShort("上传成功");
                            setFragmentResult(LvJiHomeFragment.RESULT_OK,null);
                            EventBus.getDefault().post(new EventBean("上传成功"));
                            _mActivity.pop();
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("服务器错误");
                    }
                });
    }
}
