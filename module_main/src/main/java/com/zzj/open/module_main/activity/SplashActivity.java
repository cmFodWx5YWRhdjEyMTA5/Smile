package com.zzj.open.module_main.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzj.open.base.imp.AuthenticationCallback;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.activity.MainActivity;
import com.zzj.open.module_main.fragment.FingerprintDialogFragment;

import java.security.KeyStore;
import java.util.logging.LogManager;

import cdc.sed.yff.AdManager;
import cdc.sed.yff.nm.cm.ErrorCode;
import cdc.sed.yff.nm.sp.SplashViewSettings;
import cdc.sed.yff.nm.sp.SpotListener;
import cdc.sed.yff.nm.sp.SpotManager;
import cdc.sed.yff.nm.sp.SpotRequestListener;
import me.goldze.mvvmhabit.http.interceptor.logging.Logger;


/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/7 15:23
 * @desc :
 * @version: 1.0
 */
public class SplashActivity extends AppCompatActivity implements AuthenticationCallback {

    private static final String DEFAULT_KEY_NAME = "default_key";

    KeyStore keyStore;
    private String password;
    private TextView tvDecodeInfo;
    private int purpose;
    FingerprintDialogFragment fragment;
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdManager.getInstance(this).init("71c36cd5b46e3637", "17a8d95f274dc224", true);
        initView();
//        inMain();
//        if (supportFingerprint()) {
//
//        }else {
//            inMain();
//        }
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        preloadAd();
                        setupSplashAd(); // 如果需要首次展示开屏，请注释掉本句代码
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                        finish();
                    }
                });
    }

    private void initView() {
        editText = findViewById(R.id.et_password);
        tvDecodeInfo = findViewById(R.id.tv_info);

        findViewById(R.id.btn_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = editText.getText().toString().trim();
                purpose = KeyProperties.PURPOSE_ENCRYPT;
                showFingerPrintDialog(password,KeyProperties.PURPOSE_ENCRYPT);
                Log.e("Cipher","--明文-->"+password);
            }
        });
        findViewById(R.id.btn_decode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = KeyProperties.PURPOSE_DECRYPT;
                showFingerPrintDialog("",KeyProperties.PURPOSE_DECRYPT);
            }
        });

    }

    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    private void showFingerPrintDialog(String password,int purpose) {

        fragment = new FingerprintDialogFragment();
        fragment.setCallback(this);
        fragment.setEncryptContent(password);
        fragment.setPurpose(purpose);
        fragment.show(getSupportFragmentManager(), "fingerprint");
    }

    /**
     * 进入主页面
     */
    private void inMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onAuthenticationSucceeded(String value) {

        fragment.dismiss();
        Log.e("onAuthenticationSuccess","onAuthenticationSucceeded---->"+value);
        if( purpose == KeyProperties.PURPOSE_DECRYPT){
            tvDecodeInfo.setText("解密信息："+value);
        }else {
            tvDecodeInfo.setText("加密信息："+value);
        }
//        inMain();
    }

    @Override
    public void onAuthenticationFail() {
        Log.e("onAuthenticationFail","onAuthenticationFail---->");

    }

    /**
     * 预加载广告
     */
    private void preloadAd() {
        // 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                LogUtils.e("请求插屏广告成功");
                //				// 应用安装后首次展示开屏会因为本地没有数据而跳过
                //              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
                //				setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                LogUtils.e("请求插屏广告失败，errorCode: %s", errorCode);
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        ToastUtils.showShort("网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        ToastUtils.showShort("暂无插屏广告");
                        break;
                    default:
                        ToastUtils.showShort("请稍后再试");
                        break;
                }
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器
        final LinearLayout splashLayout = (LinearLayout) findViewById(R.id.ll_ad);

        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        //		// 设置是否展示失败自动跳转，默认自动跳转
        //		splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
        // 设置跳转的窗口类
        splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        // 展示开屏广告
        SpotManager.getInstance(this)
                .showSplash(this, splashViewSettings, new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                        LogUtils.e("开屏展示成功");
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        LogUtils.e("开屏展示失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                LogUtils.e("网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                LogUtils.e("暂无开屏广告");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                LogUtils.e("开屏资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                LogUtils.e("开屏展示间隔限制");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                LogUtils.e("开屏控件处在不可见状态");
                                break;
                            default:
                                LogUtils.e("errorCode: %d", errorCode);
                                break;
                        }
                    }

                    @Override
                    public void onSpotClosed() {
                        LogUtils.e("开屏被关闭");
                    }

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                        LogUtils.e("开屏被点击");
                        LogUtils.e("是否是网页广告？%s", isWebPage ? "是" : "不是");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(this).onDestroy();
    }
}
