package com.zzj.open.base.utils;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zzj.open.base.R;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/9 14:48
 * @desc :  toolbar的帮助类
 * @version: 1.0
 */
public class ToolbarHelper {
    private BaseActivity activity;
    private Toolbar toolbar;
    private String title;

    public ToolbarHelper(Context context,Toolbar toolbar,boolean isShow){
        activity = (BaseActivity) context;
        this.toolbar = toolbar;
        isShowNavigationIcon(isShow);
    }
    public ToolbarHelper(Context context,Toolbar toolbar,String title,boolean isShow){
        activity = (BaseActivity) context;
        this.toolbar = toolbar;
        this.title = title;
        initToolbar(isShow);
    }
    public ToolbarHelper(Context context,Toolbar toolbar,String title){
        activity = (BaseActivity) context;
        this.toolbar = toolbar;
        this.title = title;
        initToolbar(true);
    }

    public void initToolbar(boolean isShow){
        isShowNavigationIcon(isShow);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.main_text_color));

    }

    public void isShowNavigationIcon(boolean isShow){
        activity.setSupportActionBar(toolbar);
        if(isShow){
            toolbar.setNavigationIcon(R.drawable.ic_back_white);
        }else {
            toolbar.setNavigationIcon(null);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    activity.pop();
                } else {
                    ActivityCompat.finishAfterTransition(activity);
                }

            }
        });
    }

}
