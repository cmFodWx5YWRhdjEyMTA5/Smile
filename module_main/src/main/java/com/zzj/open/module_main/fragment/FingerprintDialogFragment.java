package com.zzj.open.module_main.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzj.open.base.imp.AuthenticationCallback;
import com.zzj.open.base.protect.LocalAndroidKeyStore;
import com.zzj.open.module_main.R;

import javax.crypto.Cipher;

import me.goldze.mvvmhabit.utils.SPUtils;

import static com.zzj.open.base.global.SPKeyGlobal.ENCRYPT_PASSWORD_DATA;
import static com.zzj.open.base.global.SPKeyGlobal.ENCRYPT_PASSWORD_KEY;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/1/17 11:44
 * @desc :
 * @version: 1.0
 */
@TargetApi(23)
public class FingerprintDialogFragment extends DialogFragment {

    private FingerprintManager fingerprintManager;

    private CancellationSignal mCancellationSignal;


    private TextView errorMsg;

    private AuthenticationCallback callback;

    private LocalAndroidKeyStore mLocalAndroidKeyStore;
    /**
     * PURPOSE_ENCRYPT,则表示生成token，否则为取出token
     */
    private int purpose = KeyProperties.PURPOSE_ENCRYPT;
    /**
     * 这个是要加密的内容
     */
    private String encryptContent;
    /**
     * 标识是否是用户主动取消的认证。
     */
    private boolean isSelfCancelled;

    /**
     * 设置加密的内容
     *
     * @param encryptContent
     */
    public void setEncryptContent(String encryptContent) {
        this.encryptContent = encryptContent;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
        mLocalAndroidKeyStore = new LocalAndroidKeyStore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        errorMsg = v.findViewById(R.id.error_msg);
        TextView cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopListening();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始指纹认证监听
        startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止指纹认证监听
        stopListening();
    }

    @SuppressLint("MissingPermission")
    private void startListening() {
        isSelfCancelled = false;
        mCancellationSignal = new CancellationSignal();
        FingerprintManager.CryptoObject object;
        if (purpose == KeyProperties.PURPOSE_DECRYPT) {
            String IV = SPUtils.getInstance().getString(ENCRYPT_PASSWORD_KEY);
            object = mLocalAndroidKeyStore.getCryptoObject(Cipher.DECRYPT_MODE, Base64.decode(IV, Base64.URL_SAFE));
            if (object == null) {
                return;
            }
        } else {
            //在keystore中生成加密密钥
            mLocalAndroidKeyStore.generateKey(LocalAndroidKeyStore.keyName);
            object = mLocalAndroidKeyStore.getCryptoObject(Cipher.ENCRYPT_MODE, null);
        }
        fingerprintManager.authenticate(object, mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                if (!isSelfCancelled) {
                    errorMsg.setText(errString);
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        Toast.makeText(getActivity(), errString, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                errorMsg.setText(helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                Toast.makeText(getActivity(), "指纹认证成功", Toast.LENGTH_SHORT).show();
                if (callback == null) {
                    return;
                }
                if (result.getCryptoObject() == null) {
                    callback.onAuthenticationFail();
                    return;
                }
                final Cipher cipher = result.getCryptoObject().getCipher();
                if (purpose == KeyProperties.PURPOSE_DECRYPT) {
                    //取出secret key并返回
                    String data = SPUtils.getInstance().getString(ENCRYPT_PASSWORD_DATA);
                    if (TextUtils.isEmpty(data)) {
                        callback.onAuthenticationFail();
                        return;
                    }
                    try {
                        byte[] decrypted = cipher.doFinal(Base64.decode(data, Base64.URL_SAFE));
                        callback.onAuthenticationSucceeded(new String(decrypted));
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onAuthenticationFail();
                    }
                } else {
                    //将前面生成的data包装成secret key，存入沙盒
                    try {
                        byte[] encrypted = cipher.doFinal(encryptContent.getBytes());
                        byte[] IV = cipher.getIV();
                        String se = Base64.encodeToString(encrypted, Base64.URL_SAFE);
                        String siv = Base64.encodeToString(IV, Base64.URL_SAFE);
                        SPUtils.getInstance().put(ENCRYPT_PASSWORD_DATA, se);
                        SPUtils.getInstance().put(ENCRYPT_PASSWORD_KEY, siv);
                        callback.onAuthenticationSucceeded(se);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onAuthenticationFail();
                    }

                }
            }

            @Override
            public void onAuthenticationFailed() {
                errorMsg.setText("指纹认证失败，请再试一次");
                callback.onAuthenticationFail();
            }
        }, null);
    }

    private void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }

    public void setCallback(AuthenticationCallback callback) {
        this.callback = callback;
    }


}
