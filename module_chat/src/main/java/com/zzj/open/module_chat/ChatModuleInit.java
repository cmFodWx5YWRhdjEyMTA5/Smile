package com.zzj.open.module_chat;

import android.app.Application;
import android.content.Context;

import com.zzj.open.base.base.IModuleInit;
import com.zzj.open.module_chat.db.DaoMaster;
import com.zzj.open.module_chat.db.DaoSession;

import org.greenrobot.greendao.database.Database;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * @author JamesZhang .
 */

public class ChatModuleInit implements IModuleInit {

    private static DaoSession daoSession;

    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("聊天模块初始化 -- onInitAhead");
        setUpDataBase(application);
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("聊天模块初始化 -- onInitLow");
        return false;
    }


    private void setUpDataBase(Context context){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context,"chat_message");
        Database database = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
