package com.zzj.open.base.global;

/**
 * 全局SharedPreferences Key 统一存放在这里，单个组件中的key可以另外在组件中定义
 * Created by goldze on 2018/6/21 0021.
 */
public class SPKeyGlobal {

    /**
     * 请求成功的标识
     */
    public static final int REQUEST_SUCCESS = 200;
    public static final String USER_INFO = "user_info";
    //加密的key
    public static final String ENCRYPT_PASSWORD_KEY = "encrypt_password_key";
    //加密的密码
    public static final String ENCRYPT_PASSWORD_DATA = "encrypt_password_data";
}
