package com.zzj.open.module_chat.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.base.bean.UsersVO;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.HttpUrl;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.SlideCardBean;
import com.zzj.open.module_chat.utils.Cons;

import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 10:55
 * @desc :  滑动卡片的适配器
 * @version: 1.0
 */
public class ChatHomeSlideCardAdapter extends BaseQuickAdapter<UsersVO,BaseViewHolder> {


    public ChatHomeSlideCardAdapter(int layoutResId, @Nullable List<UsersVO> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UsersVO item) {
        Glide.with(mContext).load(HttpUrl.IMAGE_URL+item.getFaceImageBig()).apply(new RequestOptions()
                .placeholder(R.mipmap.bg_src_morning)).into((ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_profession,item.getProfessional());
        helper.setText(R.id.tv_age,""+item.getBswAge());
        helper.setText(R.id.tv_constellation,item.getBswConstellation());

    }
}
