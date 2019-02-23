package com.zzj.open.module_movie;

import android.app.Application;
import android.content.Context;

import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * @author JamesZhang .
 */

public class MovieModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("影视模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("影视模块初始化 -- onInitLow");
        return false;
    }
}
