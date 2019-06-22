package com.zzj.open.module_lvji.impl;

import android.view.View;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/22
 * @desc :
 * @version: 1.0
 */
public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer{
    private static final String TAG = "CurveTransformer";


    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2f);
        item.setPivotY(item.getHeight()/2f);
        float scale = 1 - 0.2f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}
