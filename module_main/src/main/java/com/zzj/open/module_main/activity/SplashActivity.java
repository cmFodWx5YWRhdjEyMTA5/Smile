package com.zzj.open.module_main.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzj.open.module_main.R;


/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/7 15:23
 * @desc :启动页
 * @version: 1.0
 */
public class SplashActivity extends AppCompatActivity  {

    private static final String DEFAULT_KEY_NAME = "default_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置线路
        if(SPUtils.getInstance().getString("currentLine","").equals("")){
            SPUtils.getInstance().put("currentLine","1");
        }

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {

                        inMain();

                    } else {
                        // At least one permission is denied
                        finish();
                    }
                });
    }

    /**
     * 进入主页面
     */
    private void inMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
