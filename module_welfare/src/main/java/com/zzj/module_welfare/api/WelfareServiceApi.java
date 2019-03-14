package com.zzj.module_welfare.api;

import com.zzj.open.base.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/14 17:38
 * @desc : 福利接口
 * @version: 1.0
 */
public interface WelfareServiceApi {

    @GET("http://gank.io/api/data/福利/10/1")
    Observable<Result> getWelfareImageList();
}
