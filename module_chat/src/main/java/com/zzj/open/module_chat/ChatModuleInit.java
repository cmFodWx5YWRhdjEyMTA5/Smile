package com.zzj.open.module_chat;

import android.app.Application;

import com.zzj.open.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * @author JamesZhang .
 */

public class ChatModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("聊天模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("聊天模块初始化 -- onInitLow");
        return false;
    }
}
