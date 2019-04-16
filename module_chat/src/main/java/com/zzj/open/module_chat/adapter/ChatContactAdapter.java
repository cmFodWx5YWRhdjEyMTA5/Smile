package com.zzj.open.module_chat.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.MyFriendBean;
import com.zzj.open.module_chat.bean.User;

import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/15 10:40
 * @desc :  联系人列表
 * @version: 1.0
 */
public class ChatContactAdapter extends BaseQuickAdapter<MyFriendBean,BaseViewHolder> {

    public ChatContactAdapter(int layoutResId, @Nullable List<MyFriendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriendBean item) {
        Glide.with(mContext).load(item.getFriendFaceImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into((ImageView) helper.getView(R.id.iv_header));
        helper.setText(R.id.tv_username,item.getFriendUsername());
    }
}
