package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dhh.websocket.RxWebSocket;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.ChatListModel;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.ChatMsg;
import com.zzj.open.module_chat.bean.DataContent;
import com.zzj.open.module_chat.bean.MyFriendModel;
import com.zzj.open.module_chat.db.ChatListModelDao;
import com.zzj.open.module_chat.db.ChatMessageModelDao;
import com.zzj.open.module_chat.db.MyFriendModelDao;
import com.zzj.open.module_chat.service.ChatMessageService;
import com.zzj.open.module_chat.utils.Cons;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.hutool.core.date.DateUtil;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @desc : 聊天列表的viewModel
 * @version: 1.0
 */
public class ChatListViewModel extends BaseViewModel {

    public List<ChatListModel> chatListModels = new ArrayList<>();
    public ObservableBoolean isUpdateList = new ObservableBoolean(false);
    public ChatListViewModel(@NonNull Application application) {
        super(application);

    }

    /**
     * 请求签收未消息列表
     */
    public void getUnReadMsgList() {
        RetrofitClient.getInstance().create(ApiService.class)
                .getUnReadMsgList(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID))
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<List<ChatMsg>>>() {
                    @Override
                    public void accept(Result<List<ChatMsg>> result) throws Exception {
                        try {
                            if (result.getCode() == 200) {
                                List<ChatMsg> chatMsgs = result.getResult();
                                if (chatMsgs != null) {
                                    String msgIds = "";
                                    //保存数据库，并设置未读
                                    for (ChatMsg chatMsg : chatMsgs) {
                                        ChatMessageModel chatMessageModel = new ChatMessageModel();
                                        chatMessageModel.setReceiverId(chatMsg.getAcceptUserId());
                                        chatMessageModel.setSenderId(chatMsg.getSendUserId());
                                        chatMessageModel.setSend(true);
                                        chatMessageModel.setRead(false);
                                        chatMessageModel.setMsg(chatMsg.getMsg());
                                        chatMessageModel.setTime(DateUtil.formatDateTime(chatMsg.getCreateTime()));
                                        chatMessageModel.setMsgId(chatMsg.getId());
                                        chatMessageModel.setType(chatMsg.getType());
                                        chatMessageModel.setItemType(chatMsg.getItemType());

                                        ChatModuleInit.getDaoSession().getChatMessageModelDao().insertOrReplace(chatMessageModel);
                                        saveChatList(chatMessageModel);
                                        msgIds = msgIds + ",";
                                        msgIds = msgIds + chatMessageModel.getMsgId();

                                    }
                                    if (SPUtils.getInstance().getInt("websocket", 0) == 1) {
                                        //签收消息
                                        //接收到消息发送消息签收通知
                                        DataContent signMessage = new DataContent();
                                        signMessage.setAction(3);
                                        signMessage.setExtand(msgIds);
                                        RxWebSocket.send(ChatMessageService.url, GsonUtils.toJson(signMessage));
                                    }
                                    initData();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    /**
     * 加载消息列表数据
     */
    public void initData() {
        chatListModels.clear();
        queryUnreadMsg();
        List<ChatListModel> chatListModels = ChatModuleInit.getDaoSession().getChatListModelDao().queryBuilder().orderDesc(ChatListModelDao.Properties.Time).list();
        this.chatListModels.addAll(chatListModels);
        //通知更新列表
        isUpdateList.set(!isUpdateList.get());
    }

    /**
     * 查询未读消息数量
     */
    private void queryUnreadMsg() {
        //设置数据库的未读消息为0
        List<ChatListModel> chatListModels = ChatModuleInit.getDaoSession().getChatListModelDao().queryBuilder().where(ChatListModelDao.Properties.UnreadNum.gt(0)).list();
        if(chatListModels!=null&& chatListModels.size()>0){
            for(ChatListModel chatListModel : chatListModels){
                chatListModel.setUnreadNum(0);
                ChatModuleInit.getDaoSession().getChatListModelDao().update(chatListModel);
            }
        }
        List<ChatMessageModel> chatMessageModels = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder().where(ChatMessageModelDao.Properties.IsRead.eq(false)).list();
        if(chatMessageModels!=null&&chatMessageModels.size()>0){
            for(ChatMessageModel chatMessageModel : chatMessageModels){
                String senderId = chatMessageModel.getSenderId();
                ChatListModel chatListModel = ChatModuleInit.getDaoSession().getChatListModelDao().queryBuilder().where(ChatListModelDao.Properties.ChatUserId.eq(senderId)).unique();
                chatListModel.setUnreadNum(chatListModel.getUnreadNum()+1);
                ChatModuleInit.getDaoSession().getChatListModelDao().update(chatListModel);
            }
        }
    }

    /**
     * 保存消息会话
     *
     * @param chatMessageModel
     */
    private void saveChatList(ChatMessageModel chatMessageModel) {
        //存储消息到会话列表数据库
        //查询当前好友信息
        MyFriendModel myFriendModel = ChatModuleInit.getDaoSession().getMyFriendModelDao().queryBuilder().where(MyFriendModelDao.Properties.FriendUserId.eq(chatMessageModel.getSenderId())).unique();
        ChatListModel chatListModel = new ChatListModel();
        chatListModel.setMsg(chatMessageModel.getMsg());
        chatListModel.setChatUserId(chatMessageModel.getSenderId());
        chatListModel.setTime(chatMessageModel.getTime());
        chatListModel.setChatFaceImage(myFriendModel.getFriendFaceImage());
        chatListModel.setChatUserName(myFriendModel.getFriendUsername());
        chatListModel.setSend(true);
        ChatModuleInit.getDaoSession().getChatListModelDao().insertOrReplace(chatListModel);
    }
}
