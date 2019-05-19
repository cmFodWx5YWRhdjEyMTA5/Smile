package com.zzj.open.module_mine.api;

import com.zzj.open.base.bean.Result;
import com.zzj.open.module_mine.bean.UsersBo;
import com.zzj.open.base.bean.UsersVO;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/11 19:07
 * @desc :个人中心的接口
 * @version: 1.0
 */
public interface MineServiceApi {

    @FormUrlEncoded
    @POST("user/feedBack")
    Observable<Result> submitFeedback(@Field("content")String content,@Field("contact")String contact);

    @POST("u/registerOrLogin")
    Observable<Result<UsersVO>> login(@Body()UsersVO vo);

    @POST("u/updateUserInfo")
    Observable<Result<UsersVO>> updateUserInfo(@Body()UsersVO vo);

    /**
     * 头像上传
     * @param bo
     * @return
     */
    @POST("u/uploadFace")
    Observable<Result<UsersVO>> uploadFace(@Body()UsersBo bo);

}
