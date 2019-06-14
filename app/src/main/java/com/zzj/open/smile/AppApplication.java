package com.zzj.open.smile;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.zzj.open.base.config.ModuleLifecycleConfig;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by zzj on 2019/1/11 0021.
 */

public class AppApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        //....
        //初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this);

        /**
         * 统一处理服务器异常
         */
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                ToastUtils.showShort("请求失败");
            }
        });
    }


}
