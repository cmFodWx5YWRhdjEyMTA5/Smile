package com.zzj.open.module_main.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zzj.open.base.imp.AuthenticationCallback;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.fragment.FingerprintDialogFragment;

import java.security.KeyStore;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/21 15:48
 * @desc :指纹加解密demo
 * @version: 1.0
 */
public class FingerprintActivity extends AppCompatActivity implements AuthenticationCallback {

    KeyStore keyStore;
    private String password;
    private TextView tvDecodeInfo;
    private int purpose;
    FingerprintDialogFragment fragment;
    EditText editText;

    public static void start(Context context) {
        Intent starter = new Intent(context, FingerprintActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_fingerprint);
        initView();
    }


    private void initView() {
        editText = findViewById(R.id.et_password);
        tvDecodeInfo = findViewById(R.id.tv_info);

        findViewById(R.id.btn_encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = editText.getText().toString().trim();
                purpose = KeyProperties.PURPOSE_ENCRYPT;
                if(supportFingerprint()){
                    showFingerPrintDialog(password,KeyProperties.PURPOSE_ENCRYPT);
                }

                Log.e("Cipher","--明文-->"+password);
            }
        });
        findViewById(R.id.btn_decode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purpose = KeyProperties.PURPOSE_DECRYPT;
                if(supportFingerprint()){
                    showFingerPrintDialog("",KeyProperties.PURPOSE_DECRYPT);
                }

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

}
