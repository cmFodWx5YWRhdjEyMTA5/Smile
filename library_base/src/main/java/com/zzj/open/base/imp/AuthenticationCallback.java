package com.zzj.open.base.imp;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/11 11:22
 * @desc :指纹认证回调
 * @version: 1.0
 */
public interface AuthenticationCallback {
    void onAuthenticationSucceeded(String value);

    void onAuthenticationFail();
}
