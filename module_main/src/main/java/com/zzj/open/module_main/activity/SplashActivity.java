package com.zzj.open.module_main.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.starrtc.starrtcsdk.api.XHClient;
import com.starrtc.starrtcsdk.api.XHCustomConfig;
import com.starrtc.starrtcsdk.apiInterface.IXHErrorCallback;
import com.starrtc.starrtcsdk.apiInterface.IXHResultCallback;
import com.starrtc.starrtcsdk.core.beauty.XHBeautyManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzj.open.base.beauty.DemoBeautyCallback;
import com.zzj.open.base.http.HttpUrl;
import com.zzj.open.base.listener.XHChatManagerListener;
import com.zzj.open.base.listener.XHGroupManagerListener;
import com.zzj.open.base.listener.XHLoginManagerListener;
import com.zzj.open.base.listener.XHVoipManagerListener;
import com.zzj.open.base.listener.XHVoipP2PManagerListener;
import com.zzj.open.base.utils.AEvent;
import com.zzj.open.module_main.R;

import java.util.Random;


/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/7 15:23
 * @desc :启动页
 * @version: 1.0
 */
public class SplashActivity extends AppCompatActivity  {


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
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA)
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
        initFree();

    }


    //开放版SDK初始化
    private void initFree(){
        if(SPUtils.getInstance().getString("userId","").equals("")){
            SPUtils.getInstance().put("userId",""+(new Random().nextInt(900000)+100000));
        }
        //初始化 开放版 无调度 直接指定Server地址
        XHCustomConfig customConfig =  XHCustomConfig.getInstance();
        customConfig.setChatroomServerUrl(HttpUrl.RTC_CHATROOM_SERVER);
        customConfig.setLiveSrcServerUrl(HttpUrl.RTC_LIVESRC_SERVER);
        customConfig.setLiveVdnServerUrl(HttpUrl.RTC_LIVEVDN_SERVER);
        customConfig.setImServereUrl(HttpUrl.RTC_IM_SERVER);
        customConfig.setVoipServerUrl(HttpUrl.RTC_VOIP_SERVER);
        customConfig.initSDKForFree(this, SPUtils.getInstance().getString("userId",""), new IXHErrorCallback() {
            @Override
            public void error(final String errMsg, Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(errMsg);
                    }
                });
            }
        },new Handler());
        customConfig.setLogDirPath(Environment.getExternalStorageDirectory().getPath()+"/starrtcLog");
        customConfig.setDefConfigOpenGLESEnable(false);
//        customConfig.setDefConfigCamera2Enable(false);
        XHClient.getInstance().getChatManager().addListener(new XHChatManagerListener());
        XHClient.getInstance().getGroupManager().addListener(new XHGroupManagerListener());
        XHClient.getInstance().getVoipManager().addListener(new XHVoipManagerListener());
        XHClient.getInstance().getVoipP2PManager().addListener(new XHVoipP2PManagerListener());
        XHClient.getInstance().getLoginManager().addListener(new XHLoginManagerListener());
        XHBeautyManager.getInstance().setBeautyDataCallback(new DemoBeautyCallback());
        XHClient.getInstance().getLoginManager().loginFree(new IXHResultCallback() {
            @Override
            public void success(Object data) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
            @Override
            public void failed(final String errMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(errMsg);
                    }
                });
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
