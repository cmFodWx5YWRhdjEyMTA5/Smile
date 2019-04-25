package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dhh.websocket.RxWebSocket;
import com.sj.emoji.EmojiBean;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.ChatModuleInit;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.adapter.ChatMessageAdapter;
import com.zzj.open.module_chat.bean.ChatListModel;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.DataContent;
import com.zzj.open.module_chat.bean.ImageInfo;
import com.zzj.open.module_chat.bean.MyFriendModel;
import com.zzj.open.module_chat.bean.User;
import com.zzj.open.module_chat.databinding.ChatFragmentChatdetailsBinding;
import com.zzj.open.module_chat.db.ChatMessageModelDao;
import com.zzj.open.module_chat.db.MyFriendModelDao;
import com.zzj.open.module_chat.service.ChatMessageService;
import com.zzj.open.module_chat.utils.Cons;
import com.zzj.open.module_chat.utils.Factory;
import com.zzj.open.module_chat.utils.PicturesCompressor;
import com.zzj.open.module_chat.utils.QqUtils;
import com.zzj.open.module_chat.utils.SimpleCommonUtils;
import com.zzj.open.module_chat.utils.StreamUtil;
import com.zzj.open.module_chat.view.ImageRecyclerView;
import com.zzj.open.module_chat.view.QqEmoticonsKeyBoard;
import com.zzj.open.module_chat.view.SimpleQqGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.hutool.core.date.DateUtil;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/31 10:14
 * @desc :  聊天 详情
 * @version: 1.0
 */
public class ChatFragment extends BaseFragment<ChatFragmentChatdetailsBinding,BaseViewModel> implements FuncLayout.OnFuncKeyBoardListener, AutoHeightLayout.OnMaxParentHeightChangeListener{


    private ImageRecyclerView imageRecyclerView;

    private String chatUserId;
    private String chatUsername;
    private String chatFaceImage;
    private ChatMessageAdapter messageAdapter;
    private List<ChatMessageModel> chatMessageModels = new ArrayList<>();
    private Map<String,ChatMessageModel> chatMessageModelMap = new HashMap<>();
    public static ChatFragment newInstance(String chatUserId,String chatUsername,String chatFaceImage) {

        Bundle args = new Bundle();
        args.putString("chatUserId",chatUserId);
        args.putString("chatUsername",chatUsername);
        args.putString("chatFaceImage",chatFaceImage);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_chatdetails;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        chatUserId = getArguments().getString("chatUserId");
        chatUsername = getArguments().getString("chatUsername");
        chatFaceImage = getArguments().getString("chatFaceImage");
        new ToolbarHelper(_mActivity,binding.toolbar,"");

        binding.toolbarTitle.setText(chatUsername);
        //设置未读消息为已读
        List<ChatMessageModel>  unReadChatMsgs = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder().where(
                ChatMessageModelDao.Properties.SenderId.eq(chatUserId),ChatMessageModelDao.Properties.IsRead.eq(false)).list();
        if(unReadChatMsgs!=null){
            for(ChatMessageModel chatMessageModel : unReadChatMsgs){
                chatMessageModel.setRead(true);
                ChatModuleInit.getDaoSession().getChatMessageModelDao().update(chatMessageModel);
            }
        }
        //查询聊天记录
        QueryBuilder<ChatMessageModel> builder = ChatModuleInit.getDaoSession().getChatMessageModelDao().queryBuilder();
        builder.whereOr(ChatMessageModelDao.Properties.SenderId.eq(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID))
                , ChatMessageModelDao.Properties.SenderId.eq(chatUserId)).orderDesc(ChatMessageModelDao.Properties.Time).limit(10);

        chatMessageModels.addAll(builder.build().list());

        Collections.reverse(chatMessageModels);

        initEmoticonsKeyBoardBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        //解决输入法弹出后recyclerView不能自动滚动的问题
        linearLayoutManager.setStackFromEnd(true);
        User user = new User();
        user.setFaceImage(chatFaceImage);
        user.setId(chatUserId);
        user.setUsername(chatUsername);
        messageAdapter = new ChatMessageAdapter(chatMessageModels,user);
        binding.recyclerView.setAdapter(messageAdapter);
        initListener();


        binding.recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
    }

    protected void initListener() {

        /**
         * 发送按钮事件
         */
        binding.kbLayout.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg =  binding.kbLayout.getEtChat().getText().toString();

                onSendTextMsg(msg);
            }
        });
        /**
         * 对输入内容的监听
         */
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //监听是否输入内容
                if(charSequence!=null&&charSequence.toString().trim().length()>0){

                    binding.ivChatAdd.setVisibility(View.GONE);
                }else {

                    binding.ivChatAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        binding.kbLayout.reset();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void initEmoticonsKeyBoardBar() {

        QqUtils.initEmoticonsEditText(binding.kbLayout.getEtChat());

        imageRecyclerView = new ImageRecyclerView(_mActivity);
        binding.kbLayout.setAdapter(QqUtils.getCommonAdapter(_mActivity, emoticonClickListener));
        binding.kbLayout.addOnFuncKeyBoardListener(this);

        binding.kbLayout.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PTT, new SimpleQqGridView(_mActivity));
//        ek_bar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PTV, new SimpleQqGridView(mActivity));
//        ek_bar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PLUG, new SimpleQqGridView(mActivity));
        binding.kbLayout.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_IMAGE,imageRecyclerView);
        imageRecyclerView.setOnItemListener(new ImageRecyclerView.OnItemListener() {
            @Override
            public void setOnItemListener(ImageInfo imageInfo, int position) {
//                ViseLog.e("setOnItemListener---->"+imageInfo.getmUri()+"---position-->"+position);
                final String imagePath = imageInfo.getmUri().getPath();
                // 进行压缩
                String cacheDir =_mActivity.getCacheDir().getAbsolutePath();
                final String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());
                Factory.runOnAsync(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 压缩工具类
                            if (PicturesCompressor.compressImage(_mActivity,imagePath, tempFile,
                                    Cons.MAX_UPLOAD_IMAGE_LENGTH)) {
                                // 上传
                                // 清理缓存
                                StreamUtil.delete(tempFile);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

//                onSendImgMsg(imageBase64);
            }
        });
        binding.kbLayout.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {

            }
        });

        binding.kbLayout.getEmoticonsToolBarView().addFixedToolItemView(true, R.mipmap.qvip_emoji_tab_more_new_pressed, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity, "ADD", Toast.LENGTH_SHORT).show();
            }
        });


    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick( binding.kbLayout.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Cons.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
//                        onSendTextMsg(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
//                        ViseLog.e("EmojiBean---->"+content);
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
//                        ViseLog.e("EmoticonEntity---->"+content);
                    }
//                    ViseLog.e("content---->"+content);
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index =  binding.kbLayout.getEtChat().getSelectionStart();
                    Editable editable =  binding.kbLayout.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public void onMaxParentHeightChange(int height) {

    }

    @Override
    public void OnFuncPop(int height) {

    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.kbLayout.reset();
    }


    /**
     * 发送文本消息
     * @param message
     */
    private void onSendTextMsg(String message){
        if(message.equalsIgnoreCase("")){
            return;
        }
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setMsg(message);
        chatMessageModel.setType(ChatMessageModel.CHAT_MSG_TYPE_TEXT);
        chatMessageModel.setReceiverId(chatUserId);
        chatMessageModel.setSenderId(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
        chatMessageModel.setRead(true);
        chatMessageModel.setSend(false);
        chatMessageModel.setTime(DateUtil.formatDateTime(new Date()));
        //发送文本消息
        DataContent content = new DataContent();
        content.setChatMsg(chatMessageModel);
        content.setAction(2);
        content.setExtand("");
        chatMessageModelMap.put(chatMessageModel.getMsgId(),chatMessageModel);
        messageAdapter.addData(chatMessageModel);
        saveChatList(chatMessageModel);
        ChatModuleInit.getDaoSession().getChatMessageModelDao().insert(chatMessageModel);
        RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(content));
        binding.recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
    }

    /**
     * 发送图片消息
     * @param message
     */
    private void onSendImgMsg(String message){
        if(message.equalsIgnoreCase("")){
            return;
        }
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setMsg(message);
        chatMessageModel.setType(ChatMessageModel.CHAT_MSG_TYPE_PIC);
        chatMessageModel.setReceiverId(chatUserId);
        chatMessageModel.setSenderId(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID));
        //发送图片消息
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(DataContent dataContent){
        //接收到聊天消息
        if(dataContent.getAction() == 2){
            messageAdapter.addData(dataContent.getChatMsg());
            binding.recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
            //接收到消息发送消息签收通知
            DataContent signMessage = new DataContent();
            signMessage.setAction(3);
            signMessage.setExtand(","+dataContent.getChatMsg().getMsgId());
            RxWebSocket.send(ChatMessageService.url,GsonUtils.toJson(signMessage));
            //聊天消息发送成功
        }else if(dataContent.getAction() == 6){
            String msgId = dataContent.getExtand();
            ChatMessageModel chatMessageModel = chatMessageModelMap.get(msgId);
            chatMessageModel.setSend(true);
            messageAdapter.notifyDataSetChanged();
        }
    }

    //存储消息到会话列表数据库
    private void saveChatList(ChatMessageModel chatMessageModel){
        //查询当前好友信息
        MyFriendModel myFriendModel = ChatModuleInit.getDaoSession().getMyFriendModelDao().queryBuilder().where(MyFriendModelDao.Properties.FriendUserId.eq(chatMessageModel.getReceiverId())).unique();
        ChatListModel chatListModel = new ChatListModel();
        chatListModel.setMsg(chatMessageModel.getMsg());
        chatListModel.setChatUserId(chatMessageModel.getReceiverId());
        chatListModel.setTime(chatMessageModel.getTime());
        chatListModel.setChatFaceImage(myFriendModel.getFriendFaceImage());
        chatListModel.setChatUserName(myFriendModel.getFriendUsername());
        chatListModel.setSend(false);
        ChatModuleInit.getDaoSession().getChatListModelDao().insertOrReplace(chatListModel);
    }
}
