package com.zzj.open.module_main;

import android.app.Application;
import android.content.Context;


import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author JamesZhang .
 */

public class MainModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("主业务模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("主业务模块初始化 -- onInitLow");
        return false;
    }
}
