package com.zzj.module_welfare.api;

import com.zzj.module_welfare.bean.GanKImageBean;
import com.zzj.open.base.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

}
