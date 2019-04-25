package com.zzj.open.module_chat.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.ChatListModel;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeView;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/22 14:08
 * @desc : 会话列表的适配器
 * @version: 1.0
 */
public class ChatListAdapter extends BaseQuickAdapter<ChatListModel,BaseViewHolder> {

    public ChatListAdapter(int layoutResId, @Nullable List<ChatListModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatListModel item) {
        helper.setText(R.id.tv_username,item.getChatUserName());
        helper.setText(R.id.tv_message,item.getMsg());
        helper.setText(R.id.tv_date,item.getTime());
        BGABadgeView badgeView = helper.getView(R.id.badge_view);

        if(item.getUnreadNum() == 0){
            badgeView.hiddenBadge();
        }else {
            badgeView.showTextBadge(item.getUnreadNum()+"");
        }

        Glide.with(mContext).load(item.getChatFaceImage()).into((ImageView) helper.getView(R.id.iv_header));
    }
}
