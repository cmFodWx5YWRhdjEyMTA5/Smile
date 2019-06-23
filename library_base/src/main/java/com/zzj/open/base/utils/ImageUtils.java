package com.zzj.open.base.utils;

import android.os.SystemClock;
import android.util.Base64;

import com.alibaba.android.arouter.utils.TextUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.zzj.open.base.base.BaseModuleInit;
import com.zzj.open.base.bean.CallBack;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.http.ServiceApi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/28 19:26
 * @desc : 图片工具
 * @version: 1.0
 */
public class ImageUtils {


    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result =EncodeUtils.base64Encode2String(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    /**
     * 将Base64编码转换为图片
     * @param base64Str
     * @param path
     * @return true
     */
    public static boolean base64ToFile(String base64Str,String path) {
        byte[] data = Base64.decode(base64Str,Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if(data[i] < 0){
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传图片
     */
    public static void uploadImage(String imagePath, BaseViewModel viewModel,CallBack<String> callBack){

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //执行上传操作
                // 进行压缩
                String cacheDir = BaseModuleInit.application.getCacheDir().getAbsolutePath();
                final String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());
                // 压缩工具类
                if (PicturesCompressor.compressImage(imagePath, tempFile,
                        UploadHelper.MAX_UPLOAD_IMAGE_LENGTH)) {

                    emitter.onNext(tempFile);
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                        RetrofitClient.getInstance().create(ServiceApi.class)
                                .uploadImage(ImageUtils.imageToBase64(s))
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
                                }).subscribe(new Consumer<Result<String>>() {
                            @Override
                            public void accept(Result<String> o) throws Exception {
                                if(o!=null&&o.getCode() == 200){
                                    StreamUtil.delete(s);
                                    callBack.success(o.getResult());
                                }else {
                                    callBack.fails("");
                                    com.blankj.utilcode.util.ToastUtils.showShort("创建失败");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                com.blankj.utilcode.util.ToastUtils.showShort("创建失败");
                                callBack.fails("");
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });





    }

}
