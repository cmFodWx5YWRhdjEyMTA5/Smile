package com.zzj.open.module_chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dhh.rxlifecycle2.RxLifecycle;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber;
import com.zzj.open.base.http.HttpsUtils;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.DataContent;
import com.zzj.open.module_chat.db.ChatMessageModelDao;
import com.zzj.open.module_chat.utils.Cons;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 消息服务
 * @version: 1.0
 */
public class ChatMessageService extends Service {

    public static final String url = "ws://192.168.2.131:8088/ws";


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("启动消息服务");
        initWebSocket();
    }

    private void initWebSocket(){
        //init config
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
                .setClient(new OkHttpClient())   //if you want to set your okhttpClient
                .setShowLog(true, "your logTag")
                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
                .setSSLSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);


        RxWebSocket.get(url)
                //RxLifecycle : https://github.com/dhhAndroid/RxLifecycle
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(@android.support.annotation.NonNull WebSocket webSocket) {
                        Log.d("MainActivity", " on WebSocket open");
                        ChatMessageModel chatMessageModel = new ChatMessageModel();
                        chatMessageModel.setSenderId(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
                        //发送文本消息
                        DataContent content = new DataContent();
                        content.setChatMsg(chatMessageModel);
                        content.setAction(1);
                        content.setExtand("");
                        RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(content));
                    }

                    @Override
                    protected void onMessage(@android.support.annotation.NonNull String text) {
                       LogUtils.e("ChatMessageService---->"+ text);
                       DataContent dataContent = GsonUtils.fromJson(text,DataContent.class);
                       // 2 聊天消息
                       if(dataContent.getAction() == 2){
                           ChatModuleInit.getDaoSession().getChatMessageModelDao().insert(dataContent.getChatMsg());
                           EventBus.getDefault().post(dataContent);
                       }else if(dataContent.getAction() == 6){
                           String msgId = dataContent.getExtand();
                           ChatMessageModel chatMessageModel = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder().where(ChatMessageModelDao.Properties.MsgId.eq(msgId)).unique();
                           chatMessageModel.setSend(true);
                           ChatModuleInit.getDaoSession().getChatMessageModelDao().update(chatMessageModel);
                           EventBus.getDefault().post(dataContent);
                       }

                    }

                    @Override
                    protected void onMessage(@android.support.annotation.NonNull ByteString byteString) {
                        LogUtils.e("ChatMessageService---->"+ byteString.toString());
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "onReconnect");
                    }

                    @Override
                    protected void onClose() {
                        Log.d("MainActivity", "onClose");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
