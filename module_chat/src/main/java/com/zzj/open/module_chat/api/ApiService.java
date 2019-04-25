package com.zzj.open.module_chat.api;

import com.zzj.open.base.bean.Result;
import com.zzj.open.module_chat.bean.ChatMessageModel;
import com.zzj.open.module_chat.bean.ChatMsg;
import com.zzj.open.module_chat.bean.FriendRequestBean;
import com.zzj.open.module_chat.bean.MyFriendModel;
import com.zzj.open.module_chat.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return
     */
    @GET("u/getUserInfo")
    Observable<Result<User>> getUserInfo(@Query("userId") String userId);
    /**
     * 添加好友到通讯录
     * @param myUserId 我的用户id
     * @param friendUsername 用户名
     * @return
     */
    @FormUrlEncoded
    @POST("u/addFriendRequest")
    Observable<Result> addFriendRequest(@Field("myUserId") String myUserId,@Field("friendUsername") String friendUsername);
    /**
     * 获取好友请求列表
     * @param userId 我的用户id
     * @return
     */
    @FormUrlEncoded
    @POST("u/queryFriendRequests")
    Observable<Result<List<FriendRequestBean>>> queryFriendRequests(@Field("userId") String userId);
    /**
     * 好友的请求  接受或忽略
     * @param acceptUserId 我的用户id
     * @return
     */
    @FormUrlEncoded
    @POST("u/operFriendRequest")
    Observable<Result<List<MyFriendModel>>> operFriendRequest(@Field("acceptUserId") String acceptUserId, @Field("sendUserId")String sendUserId,
                                                              @Field("operType")int operType);
    /**
     * 我的好友列表
     * @param userId 我的用户id
     * @return
     */
    @FormUrlEncoded
    @POST("u/myFriends")
    Observable<Result<List<MyFriendModel>>> myFriends(@Field("userId") String userId);
    /**
     * 获取未签收的消息列表
     * @param acceptUserId 我的用户id
     * @return
     */
    @FormUrlEncoded
    @POST("u/getUnReadMsgList")
    Observable<Result<List<ChatMsg>>> getUnReadMsgList(@Field("acceptUserId") String acceptUserId);
}
