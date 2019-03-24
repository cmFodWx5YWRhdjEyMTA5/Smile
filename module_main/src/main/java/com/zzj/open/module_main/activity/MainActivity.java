package com.zzj.open.module_main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhh.rxlifecycle2.RxLifecycle;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber;
import com.zzj.open.base.http.HttpsUtils;
import com.zzj.open.base.router.RouterActivityPath;
import com.zzj.open.module_main.BR;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.databinding.ActivityMainBinding;
import com.zzj.open.module_main.fragment.MainFragment;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/28 14:47
 * @desc :
 * @version: 1.0
 */
@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    String url = "ws://192.168.0.108:8088/ws";

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }
    @Override
    public void initData() {
        super.initData();
        RxLifecycle.injectRxLifecycle(this);
        initWebSocket();
//        keystore();
        loadRootFragment(R.id.fl_container,new MainFragment());
    }



    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    private void initWebSocket(){
        //init config
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
                .setClient(new OkHttpClient())   //if you want to set your okhttpClient
                .setShowLog(true, "your logTag")
                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
                .setSSLSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);


        RxWebSocket.get(url)
                //RxLifecycle : https://github.com/dhhAndroid/RxLifecycle
                .compose(RxLifecycle.with(this).<WebSocketInfo>bindOnDestroy())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(@android.support.annotation.NonNull WebSocket webSocket) {
                        Log.d("MainActivity", " on WebSocket open");
                        RxWebSocket.send(url,"hello word");
                    }

                    @Override
                    protected void onMessage(@android.support.annotation.NonNull String text) {
                        Log.d("MainActivity", text);
//                        textview.setText(Html.fromHtml(text));
                    }

                    @Override
                    protected void onMessage(@android.support.annotation.NonNull ByteString byteString) {
                        Log.d("MainActivity", byteString.toString());
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "onReconnect");
                    }

                    @Override
                    protected void onClose() {
                        Log.d("MainActivity", "onClose");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }
    /**
     * keyStore加密解密
     */
    public static void keystore() {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(64);
            //生成对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyData = secretKey.getEncoded();
            System.out.println(keyData.length);
            //日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
            String keyInBase64 = Base64.encodeToString(keyData, Base64.DEFAULT);
            System.out.println(keyInBase64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
