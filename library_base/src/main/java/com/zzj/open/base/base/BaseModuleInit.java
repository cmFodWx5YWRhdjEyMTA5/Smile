package com.zzj.open.base.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.lzy.ninegrid.NineGridView;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.zzj.open.base.BuildConfig;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * Created by goldze on 2018/6/21 0021.
 * 基础库自身初始化操作
 */

public class BaseModuleInit implements IModuleInit {
    public static  Application application;

    private static BaseModuleInit instance;


    @Override
    public boolean onInitAhead(Application application) {
        MultiDex.install(application);
        this.application = application;
        //开启打印日志
        KLog.init(true);
        //初始化阿里路由框架
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
        KLog.e("基础层初始化 -- onInitAhead");

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(application.getApplicationContext(),  cb);

        CrashReport.initCrashReport(application.getApplicationContext(), "9ad07ee8f6", BuildConfig.DEBUG);
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("基础层初始化 -- onInitLow");
//        SkinCompatManager.withoutActivity(application)                         // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
//                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
//                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
//                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
//                .loadSkin();
        NineGridView.setImageLoader(new GlideImageLoader());
        return false;
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy() {
        BaseModuleInit app = getInstance();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(application);
    }

    public static BaseModuleInit getInstance() {
        if(instance == null){
            instance = new BaseModuleInit();
        }
        return instance;
    }


    /** Picasso 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {

            Glide.with(context).load(url).into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
