package com.zzj.open.base.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zzj.open.base.R;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/9 14:48
 * @desc :  toolbar的帮助类
 * @version: 1.0
 */
public class ToolbarHelper {
    private Activity activity;
    private Toolbar toolbar;
    private String title;

    public ToolbarHelper(Context context,Toolbar toolbar,boolean isShow){
        activity = (Activity) context;
        this.toolbar = toolbar;
        isShowNavigationIcon(isShow);
    }
    public ToolbarHelper(Context context,Toolbar toolbar,String title){
        activity = (Activity) context;
        this.toolbar = toolbar;
        this.title = title;
        initToolbar();
    }

    public void initToolbar(){
        isShowNavigationIcon(true);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));

    }

    public void isShowNavigationIcon(boolean isShow){
        if(isShow){
            toolbar.setNavigationIcon(R.drawable.ic_back_white);
        }else {
            toolbar.setNavigationIcon(null);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

}
