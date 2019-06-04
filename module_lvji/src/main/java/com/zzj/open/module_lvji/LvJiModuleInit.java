package com.zzj.open.module_lvji;

import android.app.Application;

import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/4 19:07
 * @desc :
 * @version: 1.0
 */
public class LvJiModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("旅记模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("旅记模块初始化 -- onInitLow");
        return false;
    }
}
