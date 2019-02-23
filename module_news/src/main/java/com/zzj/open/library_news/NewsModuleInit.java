package com.zzj.open.library_news;

import android.app.Application;
import android.content.Context;

import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * Created by goldze on 2018/6/21 0021.
 */

public class NewsModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("新闻模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("新闻模块初始化 -- onInitLow");
        return false;
    }
}
