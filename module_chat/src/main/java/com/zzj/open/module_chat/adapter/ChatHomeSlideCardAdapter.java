package com.zzj.open.module_chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_chat.bean.SlideCardBean;

import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 10:55
 * @desc :  滑动卡片的适配器
 * @version: 1.0
 */
public class ChatHomeSlideCardAdapter extends BaseQuickAdapter<SlideCardBean,BaseViewHolder> {


    public ChatHomeSlideCardAdapter(int layoutResId, @Nullable List<SlideCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SlideCardBean item) {

    }
}
