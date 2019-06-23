package com.zzj.open.module_lvji.api;

import com.zzj.open.base.bean.Result;
import com.zzj.open.module_lvji.model.LvjiPublishModel;
import com.zzj.open.module_lvji.model.LvjiTopicModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/13 16:49
 * @desc :  lvji接口
 * @version: 1.0
 */
public interface ApiService {

    /**
     * 上传分享
     * @param myUserId 用户id
     * @param imageUrlList 图片url集合
     * @param content 发送的内容
     * @return
     */
    @FormUrlEncoded
    @POST("lvji/publish")
    Observable<Result<LvjiPublishModel>> publish(@Field("userId") String myUserId,@Field("location")String location
            , @Field("imageUrlList") String imageUrlList,@Field("content")String content,@Field("city")String city,@Field("topic")String topic);

    @GET("lvji/getPublishList")
    Observable<Result<List<LvjiPublishModel>>> getPublishList(@Query("page") int page,@Query("pagesize") int pagesize);

    /**
     * 获取话题列表
     * @return
     */
    @GET("lvji/getTopicList")
    Observable<Result<List<LvjiTopicModel>>> getTopicList();

    /**
     * 创建话题
     * @param lvjiTopicModel
     * @return
     */
    @POST("lvji/createTopic")
    Observable<Result<LvjiTopicModel>> createTopic(@Body LvjiTopicModel lvjiTopicModel);
}
