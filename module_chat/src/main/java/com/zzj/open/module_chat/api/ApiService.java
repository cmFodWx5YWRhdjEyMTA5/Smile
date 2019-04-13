package com.zzj.open.module_chat.api;

import com.zzj.open.base.bean.Result;
import com.zzj.open.module_chat.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/13 16:49
 * @desc :  聊天接口
 * @version: 1.0
 */
public interface ApiService {

    /**
     * 搜索好友
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @FormUrlEncoded
    @POST("u/search")
    Observable<Result<User>> searchUser(@Field("myUserId") String myUserId,@Field("friendUsername") String friendUsername);
}
