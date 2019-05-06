package com.zzj.open.module_chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.zzj.open.base.http.HttpsUtils;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.bean.ChatListModel;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.DataContent;
import com.zzj.open.module_chat.bean.GroupCard;
import com.zzj.open.module_chat.bean.MyFriendModel;
import com.zzj.open.module_chat.db.ChatMessageModelDao;
import com.zzj.open.module_chat.db.GroupCardDao;
import com.zzj.open.module_chat.db.MyFriendModelDao;
import com.zzj.open.module_chat.utils.Cons;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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

//    public static final String url = "ws://192.168.0.107:8088/ws";
    public static final String url = "ws://192.168.2.129:8088/ws";


    private Timer timer;
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
                        startHeartBeatCheck();
                        ChatMessageModel chatMessageModel = new ChatMessageModel();
                        chatMessageModel.setSenderId(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
                        //发送文本消息
                        DataContent content = new DataContent();
                        content.setChatMsg(chatMessageModel);
                        content.setAction(1);
                        content.setExtand("");
                        RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(content));
                        if(SPUtils.getInstance().getInt("websocket",0) == 0){
                            EventBus.getDefault().post(content);
                        }

                        //查询数据库是否有未发送的消息，进行发送
//                        unSendMessage();


                    }

                    @Override
                    protected void onMessage(@android.support.annotation.NonNull String text) {
                       LogUtils.e("ChatMessageService---->"+ text);

                       DataContent dataContent = GsonUtils.fromJson(text,DataContent.class);
                       // 2 聊天消息
                       if(dataContent.getAction() == 2){
                           ChatMessageModel chatMessageModel = dataContent.getChatMsg();
                           ChatModuleInit.getDaoSession().getChatMessageModelDao().insert(chatMessageModel);
                           //存储消息到会话列表数据库

                           ChatListModel chatListModel = new ChatListModel();
                           chatListModel.setMsg(chatMessageModel.getMsg());
                           chatListModel.setChatUserId(chatMessageModel.getSenderId());
                           chatListModel.setTime(chatMessageModel.getTime());
                           // 群聊  单聊
                           if(chatMessageModel.getChatType() == 0){
                               //查询当前好友信息
                               MyFriendModel myFriendModel = ChatModuleInit.getDaoSession().getMyFriendModelDao().queryBuilder().where(MyFriendModelDao.Properties.FriendUserId.eq(chatMessageModel.getSenderId())).unique();
                               chatListModel.setChatFaceImage(myFriendModel.getFriendFaceImage());
                               chatListModel.setChatUserName(myFriendModel.getFriendUsername());
                           }else if(chatMessageModel.getChatType() == 1){
                               GroupCard groupCard = ChatModuleInit.getDaoSession().getGroupCardDao().queryBuilder().where(GroupCardDao.Properties.Id.eq(chatMessageModel.getSenderId())).unique();
                               chatListModel.setChatFaceImage(groupCard.getPicture());
                               chatListModel.setChatUserName(groupCard.getName());
                           }
                           chatListModel.setChatType(chatMessageModel.getChatType());
                           chatListModel.setSend(true);
                           ChatModuleInit.getDaoSession().getChatListModelDao().insertOrReplace(chatListModel);
                           EventBus.getDefault().post(dataContent);
                           //设置消息发送成功 dataContent.getAction() == 6
                       }else if(dataContent.getAction() == 6){
                           String msgId = dataContent.getExtand();
                           ChatMessageModel chatMessageModel = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder().where(ChatMessageModelDao.Properties.MsgId.eq(msgId)).unique();
                           chatMessageModel.setSend(true);
                           ChatModuleInit.getDaoSession().getChatMessageModelDao().update(chatMessageModel);
                           EventBus.getDefault().post(dataContent);
                           saveChatList(chatMessageModel);
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
                        SPUtils.getInstance().put("websocket",0);
                        Log.d("MainActivity", "onClose");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }

    private void startHeartBeatCheck() {
        DataContent dataContent = new DataContent();
        dataContent.setAction(4);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(dataContent));
            }
        },2000,2000);
    }

    /**
     * 查询当前10分钟内未发送的消息进行发送
     */
    private void unSendMessage() {
        List<ChatMessageModel> chatMessageModels = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder().where(
                ChatMessageModelDao.Properties.IsSend.eq(false)).list();
        if(chatMessageModels!=null){
            for (ChatMessageModel chatMessageModel : chatMessageModels) {
                if(TimeUtils.string2Millis(chatMessageModel.getTime())>(System.currentTimeMillis()-60*1000)){
                    DataContent content = new DataContent();
                    content.setChatMsg(chatMessageModel);
                    //判断群聊还是单聊
                    if(chatMessageModel.getChatType() == 0){
                        content.setAction(2);
                    }else if(chatMessageModel.getChatType() == 1){
                        content.setAction(7);
                    }
                    content.setExtand("");
                    RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(content));
                }else {
                    chatMessageModel.setSendFails(true);
                    ChatModuleInit.getDaoSession().getChatMessageModelDao().update(chatMessageModel);
                    DataContent content = new DataContent();
                    content.setChatMsg(chatMessageModel);
                    content.setAction(6);
                    content.setExtand("");
                    EventBus.getDefault().post(content);
                }
            }
        }
    }

    //存储消息到会话列表数据库
    private void saveChatList(ChatMessageModel chatMessageModel){
        ChatListModel chatListModel = new ChatListModel();
        chatListModel.setMsg(chatMessageModel.getMsg());
        chatListModel.setChatUserId(chatMessageModel.getReceiverId());
        chatListModel.setTime(chatMessageModel.getTime());
        // 群聊  单聊
        if(chatMessageModel.getChatType() == 0){
            //查询当前好友信息
            MyFriendModel myFriendModel = ChatModuleInit.getDaoSession().getMyFriendModelDao().queryBuilder().where(MyFriendModelDao.Properties.FriendUserId.eq(chatMessageModel.getReceiverId())).unique();
            chatListModel.setChatFaceImage(myFriendModel.getFriendFaceImage());
            chatListModel.setChatUserName(myFriendModel.getFriendUsername());
        }else if(chatMessageModel.getChatType() == 1){
            GroupCard groupCard = ChatModuleInit.getDaoSession().getGroupCardDao().queryBuilder().where(GroupCardDao.Properties.Id.eq(chatMessageModel.getReceiverId())).unique();
            chatListModel.setChatFaceImage(groupCard.getPicture());
            chatListModel.setChatUserName(groupCard.getName());
        }
        chatListModel.setSend(true);
        chatListModel.setChatType(chatMessageModel.getChatType());
        ChatModuleInit.getDaoSession().getChatListModelDao().insertOrReplace(chatListModel);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SPUtils.getInstance().put("websocket",0);
    }
}
