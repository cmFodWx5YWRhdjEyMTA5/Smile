package com.zzj.open.base.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zzj.open.base.R;
import com.zzj.open.base.base.BaseModuleInit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 头像控件
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setup( String url) {
        setup( R.mipmap.default_head, url);
    }


    public void setup( int resourceId, String url) {
        if (url == null)
            url = "";
        Glide.with(BaseModuleInit.application).load(url)
                .apply(new RequestOptions().centerCrop().dontAnimate().placeholder(resourceId)).into(this);

    }

}
