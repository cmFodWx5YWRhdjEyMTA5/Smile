package com.zzj.open.library_news.network;

import com.zzj.open.library_news.bean.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/12 12:37
 * @desc : 新闻接口地址
 * @version: 1.0
 */
public interface NewsService {

//    http://v.juhe.cn/toutiao/index?type=top&key=APPKEY

    @GET("http://v.juhe.cn/toutiao/index?type=top&key=8d16f462a28ba044216e056a7ef4e79e")
    Observable<NewsBean> getNewsData();
}
