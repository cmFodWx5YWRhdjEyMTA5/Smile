package com.zzj.open.base.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.danikula.videocache.HttpProxyCacheServer;
import com.zzj.open.base.BuildConfig;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * Created by goldze on 2018/6/21 0021.
 * 基础库自身初始化操作
 */

public class BaseModuleInit implements IModuleInit {
    public Application application;

    private static BaseModuleInit instance;


    @Override
    public boolean onInitAhead(Application application) {
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
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("基础层初始化 -- onInitLow");
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

}
