package com.zzj.open.base.http;

import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UpdateVersion;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/15 22:25
 * @desc :   接口
 * @version: 1.0
 */
public interface ServiceApi {


    @GET("updateVersion/getVersion")
    Observable<Result<UpdateVersion>> getVersion();

    /**
     * 上传图片
     * @return
     */
    @FormUrlEncoded
    @POST("u/uploadImage")
    Observable<Result<String>> uploadImage(@Field("base64Image") String base64Image);

}
