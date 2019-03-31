package com.zzj.module_welfare.api;

import com.zzj.module_welfare.bean.GanKImageBean;
import com.zzj.module_welfare.bean.WelfareMovieDetailsBean;
import com.zzj.open.base.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 17:38
 * @desc : 福利接口
 * @version: 1.0
 */
public interface WelfareServiceApi {

    @GET("http://gank.io/api/data/福利/20/{page}")
    Observable<GanKImageBean> getWelfareImageList(@Path("page") int page);
    @GET("welfare/getVideoList")
    Observable<Result<List<WelfareMovieDetailsBean>>> getWelfareVideoList(@Query("type") String type, @Query("page") int page, @Query("limit") int limit);

}
