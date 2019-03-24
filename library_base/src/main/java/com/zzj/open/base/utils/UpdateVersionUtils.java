package com.zzj.open.base.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.zzj.open.base.bean.UpdateVersion;

import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import okhttp3.ResponseBody;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/15 23:57
 * @desc : 版本升级工具
 * @version: 1.0
 */
public class UpdateVersionUtils {

    public static void checkVersion(final Context context, final UpdateVersion updateVersion){
        if(updateVersion.getVersionCode()> AppUtils.getAppVersionCode()){
            new AlertDialog.Builder(context)
                    .setTitle("版本升级")
                    .setMessage(updateVersion.getVersionDesc()).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setPositiveButton("升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if(NetworkUtils.isWifiConnected()){
                        downloadApk(context,updateVersion.getDownLoadUrl());
                    }else {
                        new AlertDialog.Builder(context)
                                .setMessage("您当前使用的移动网络，是否继续更新")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        downloadApk(context,updateVersion.getDownLoadUrl());
                                    }
                                }).setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }
                }
            }).show();
        }

    }

    public static void downloadApk(Context context,String apkUrl){
        String destFileDir = context.getCacheDir().getPath();  //文件存放的路径
        String destFileName = System.currentTimeMillis() + ".apk";//文件存放的名称
        DownLoadManager.getInstance().load(apkUrl, new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void onStart() {
                //RxJava的onStart()

            }

            @Override
            public void onCompleted() {
                //RxJava的onCompleted()
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                //下载成功的回调
            }

            @Override
            public void progress(final long progress, final long total) {
                //下载中的回调 progress：当前进度 ，total：文件总大小
                LogUtils.e("下载中的回调 progress：--->"+progress);

            }

            @Override
            public void onError(Throwable e) {
                //下载错误回调
            }
        });
    }

}
