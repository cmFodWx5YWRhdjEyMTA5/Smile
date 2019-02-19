package com.zzj.open.module_movie.api;

import com.zzj.open.base.bean.Result;
import com.zzj.open.module_movie.bean.MovieBean;
import com.zzj.open.module_movie.bean.MovieDetailsBean;

import java.util.List;

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
    /**
     * 获取电影列表
     * @param type
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("movie/newMovie")
    Observable<Result<List<MovieBean>>> getNesMovieList(@Field("type") String type, @Field("page")int page);
    /**
     * 获取电影详情
     * @return
     */
    @FormUrlEncoded
    @POST("movie/getMovieDetails")
    Observable<Result<MovieDetailsBean>> getMovieDetails(@Field("movieId") String movieId);
}
