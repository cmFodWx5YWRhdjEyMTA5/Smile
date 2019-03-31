package com.zzj.open.module_chat.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.ImageInfo;

import java.util.List;

public class ChatImagesAdapter extends BaseQuickAdapter<ImageInfo,BaseViewHolder> {


    public ChatImagesAdapter(int layoutResId, @Nullable List<ImageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageInfo item) {
//        ((ImageView)helper.getView(R.id.iv_chat_image)).setImageURI(item.getmUri());

        Glide.with(mContext).load(item.getThumbnail_path()).thumbnail(0.2f).into((ImageView) helper.getView(R.id.iv_chat_image));
    }
}
