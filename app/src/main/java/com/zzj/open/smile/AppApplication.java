package com.zzj.open.smile;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.zzj.open.base.config.ModuleLifecycleConfig;

import me.goldze.mvvmhabit.base.BaseApplication;

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
    }


}
