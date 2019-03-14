package com.zzj.module_welfare;

import android.app.Application;

import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * 福利
 */

public class WelfareModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("福利模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("福利模块初始化 -- onInitLow");
        return false;
    }
}
