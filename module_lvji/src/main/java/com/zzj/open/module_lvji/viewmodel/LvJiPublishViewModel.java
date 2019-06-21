package com.zzj.open.module_lvji.viewmodel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zzj.open.base.base.BaseModuleInit;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.PicturesCompressor;
import com.zzj.open.base.utils.StreamUtil;
import com.zzj.open.base.utils.UploadHelper;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.fragment.LvJiHomeFragment;
import com.zzj.open.module_lvji.model.EventBean;
import com.zzj.open.module_lvji.model.LvjiPublishModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/17
 * @desc :
 * @version: 1.0
 */
public class LvJiPublishViewModel extends BaseViewModel {
    /**
     * 上传图片的url
     */
    private ArrayList<String> imageUrlList = new ArrayList<>();

    public ObservableBoolean aBoolean = new ObservableBoolean(true);

    public LvJiPublishViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 上传图片
     * @param imageList
     */
    public void uploadingPicture(List<String> imageList,String location,String content,String city,String topic){
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
                        publish(location, content, city, topic);
                    }
                });
    }

    /**
     * 发布动态
     */
    private void publish(String location,String content,String city,String topic){
        RetrofitClient.getInstance().create(ApiService.class)
                .publish(SPUtils.getInstance().getString("userId"),location,imageUrlList.toString()
                        ,content,city,topic)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<LvjiPublishModel>>() {
                    @Override
                    public void accept(Result<LvjiPublishModel> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            ToastUtils.showShort("上传成功");
                            EventBus.getDefault().post(new EventBean("上传成功"));
                            aBoolean.set(!aBoolean.get());
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
