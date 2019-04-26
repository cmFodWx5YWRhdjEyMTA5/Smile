package com.zzj.open.module_chat.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.User;
import com.zzj.open.module_chat.utils.Cons;
import com.zzj.open.module_chat.utils.QqUtils;


import java.util.List;

//import com.sunfusheng.GlideImageView;

/**
 * Created by zzj on 2018/5/10.
 */

public class ChatMessageAdapter extends BaseMultiItemQuickAdapter<ChatMessageModel, BaseViewHolder> {

    private User toUser;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatMessageAdapter(List<ChatMessageModel> data, User toUser) {
        super(data);
        this.toUser = toUser;
        addItemType(ChatMessageModel.CHAT_MSG_TYPE_LEFT_TEXT,R.layout.item_chat_your_msg);
        addItemType(ChatMessageModel.CHAT_MSG_TYPE_RIGHT_TEXT,R.layout.item_chat_msg_my);
        addItemType(ChatMessageModel.CHAT_MSG_TYPE_LEFT_PIC,R.layout.cell_chat_pic_left);
        addItemType(ChatMessageModel.CHAT_MSG_TYPE_RIGHT_TPIC,R.layout.cell_chat_pic_right);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMessageModel item) {

        if(item.getSenderId().equalsIgnoreCase(SPUtils.getInstance().getString(Cons.SaveKey.USER_ID))){
            Glide.with(mContext).load(SPUtils.getInstance().getString(Cons.SaveKey.USER_HEADER_PIC)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                   .into((ImageView) helper.getView(R.id.pv_chat_header));

//            GlideUtils.loadImage(mContext,SPUtils.getInstance().getString(Cons.SaveKey.USER_HEADER_PIC),(ImageView) helper.getView(R.id.pv_chat_header));
        }else {
//            Glide.with(mContext).load(toUser.getPortrait())
//                    .fitCenter().placeholder(R.mipmap.header).into((ImageView) helper.getView(R.id.pv_chat_header));
            Glide.with(mContext).load(toUser.getFaceImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                    .into((ImageView) helper.getView(R.id.pv_chat_header));
//            GlideUtils.loadImage(mContext,toUser.getFaceImage(),(ImageView) helper.getView(R.id.pv_chat_header));
        }
        switch (helper.getItemViewType()){
            case ChatMessageModel.CHAT_MSG_TYPE_RIGHT_TEXT:
            case ChatMessageModel.CHAT_MSG_TYPE_LEFT_TEXT:
//                helper.setText(R.id.tv_chat_msg,item.getContent());
                //设置发送状态
                if(item.isSend()){
                    helper.setGone(R.id.loading,false);
                }else {
                    helper.setGone(R.id.loading,true);
                }
                if(item.getSendFails() == true){
                    helper.setGone(R.id.iv_send_fail,true);
                }else {
                    helper.setGone(R.id.iv_send_fail,false);
                }
                helper.setText(R.id.tv_time,item.getTime());
                QqUtils.spannableEmoticonFilter((TextView) helper.getView(R.id.tv_chat_msg), item.getMsg());
                return;
            case ChatMessageModel.CHAT_MSG_TYPE_LEFT_PIC:
            case ChatMessageModel.CHAT_MSG_TYPE_RIGHT_TPIC:
//                    helper.setImageResource(R.id.im_image,R.drawable.bg_come);
//                Glide.with(mContext).load(item.getContent()).fitCenter().into((ImageView) helper.getView(R.id.im_image));
//                GlideImageView image31 = helper.getView(R.id.image31);
//               image31.centerCrop().error(R.mipmap.image_load_err).diskCacheStrategy(DiskCacheStrategy.NONE).load(girl, R.color.placeholder, (isComplete, percentage, bytesRead, totalBytes) -> {
////            Log.d("--->", "load percentage: " + percentage + " totalBytes: " + totalBytes + " bytesRead: " + bytesRead);
//                    if (isComplete) {
//                        progressView1.setVisibility(View.GONE);
//                    } else {
//                        progressView1.setVisibility(View.VISIBLE);
//                        progressView1.setProgress(percentage);
//                    }
//                });
                return;
        }



    }
}
