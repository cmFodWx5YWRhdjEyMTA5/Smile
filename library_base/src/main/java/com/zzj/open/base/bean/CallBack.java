package com.zzj.open.base.bean;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/19 20:54
 * @desc :
 * @version: 1.0
 */
public interface CallBack<T> {

    void success(T result);

    void fails(String error);
}
