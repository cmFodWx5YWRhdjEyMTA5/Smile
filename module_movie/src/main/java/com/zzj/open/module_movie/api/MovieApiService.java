package com.zzj.open.module_movie.api;

import com.zzj.open.module_movie.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/14 12:40
 * @desc :
 * @version: 1.0
 */
public interface MovieApiService {

    /**
     * 获取电影列表
     * @param type
     * @param page
     * @param pagesize
     * @return
     */
    @FormUrlEncoded
    @POST("http://123.207.150.253/ygcms/getLineMovie.php")
    Observable<MovieBean> getMovieList(@Field("type") String type, @Field("page")int page, @Field("pagesize")int pagesize);
}
