package com.zzj.open.base.http.upload;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/13 11:19
 * @desc :
 * @version: 1.0
 */
public interface UploadFileApi {

    String UPLOAD_FILE_URL = "http:211.87.227.119:8080/api/" + "files/upload";

    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body MultipartBody body);

}
