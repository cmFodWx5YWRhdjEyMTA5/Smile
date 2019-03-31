package com.zzj.open.module_chat.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by JamesZhang on 2018/5/17.
 */

public class Factory {

    private static final String TAG = Factory.class.getSimpleName();
    // 单例模式
    private static final Factory instance;
    // 全局的线程池
    private final Executor executor;
    static {
        instance = new Factory();
    }
    private Factory() {
        // 新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);

    }
    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        instance.executor.execute(runnable);
    }

    /**
     * 如果adapter数据为空显示空界面
     * @param context
     * @param adapter
     */
//    public static void addPlaceHolderLayout(Context context, BaseQuickAdapter adapter){
//        if(adapter.getItemCount() == 0 ){
//            View view = LayoutInflater.from(context).inflate(R.layout.include_no_data,null);
//            adapter.setEmptyView(view);
//        }
//    }

}
