package com.zzj.open.base.config;

/**
 * Created by goldze on 2018/6/21 0021.
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {
    private static final String BaseInit = "com.zzj.open.base.base.BaseModuleInit";
    /**
        主业务模块
     */
    private static final String MainInit = "com.zzj.open.module_main.MainModuleInit";
    /**
     *  新闻模块
     */

    private static final String NewsInit = "com.zzj.open.library_news.NewsModuleInit";
    /**
     * 指纹模块
     */
    private static final String FingerPrintInit = "com.zzj.open.module_fingerprint.FingerPrintModuleInit";
    /**
     * 影视模块
     */
    private static final String MovieInit = "com.zzj.open.module_movie.MovieModuleInit";
    /**
     * 我的模块
     */
    private static final String MineInit = "com.zzj.open.module_mine.MineModuleInit";
    /**
     * 我的模块
     */
    private static final String WelfareInit = "com.zzj.module_welfare.WelfareModuleInit";
    /**
     * 聊天的模块
     */
    private static final String ChatInit = "com.zzj.open.module_chat.ChatModuleInit";
    /**
     * 旅记的模块
     */
    private static final String LvJiInit = "com.zzj.open.module_lvji.LvJiModuleInit";


    public static String[] initModuleNames = {BaseInit, MainInit,NewsInit,MovieInit,WelfareInit,MineInit,ChatInit,LvJiInit};
}
