package com.zzj.open.base.http;


import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import javax.crypto.SecretKey;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import me.goldze.mvvmhabit.http.interceptor.BaseInterceptor;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/19 19:24
 * @desc :
 * @version: 1.0
 */
public class EncryptInterceptor extends BaseInterceptor {

    public EncryptInterceptor(Map<String, String> headers) {
        super(headers);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //这个是请求的url，也就是咱们前面配置的baseUrl
        String url = request.url().toString();
        //这个是请求方法
        String method = request.method();
        long t1 = System.nanoTime();
//        request = encrypt(request);//模拟的加密方法
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        response = decrypt(response);
        return response;
    }

    //加密
    private Request encrypt(Request request) throws IOException {
        //获取请求body，只有@Body 参数的requestBody 才不会为 null
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            okio.Buffer buffer = new okio.Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }

            String string = buffer.readString(charset);
            //模拟加密的方法，这里调用大家自己的加密方法就可以了
            String encryptStr = encrypt(string);
            RequestBody body = MultipartBody.create(contentType, encryptStr);
            request = request.newBuilder()
                    .post(body)
                    .build();

        }
        return request;
    }

    //模拟加密的方法
    private String encrypt(String string) {
        return  string;
    }

    /**
     * 解密
     * 公钥base64-->
     * MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEJ4072HCyURCbc3vfRdzlYCD20xDspYe8fKvfXWAavnSu8MMRIxn/uUG/Ii1oBkULj/DeE56uHMduy9cSyIJBpQ==
     * @param response
     * @return
     * @throws IOException
     */
    private Response decrypt(Response response) throws IOException {
        if (response.isSuccessful()) {
            //the response data
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String string = buffer.clone().readString(charset);
            //解密方法，需要自己去实现
            String bodyString = "";
            try{
                 bodyString = decrypt(string);
            }catch (Exception e){
                e.printStackTrace();
            }

            ResponseBody responseBody = ResponseBody.create(contentType, bodyString);
            response = response.newBuilder().body(responseBody).build();
        }
        return response;
    }

    //模拟解密的方法
    private String decrypt(String string) {
        if (string != null && string.length() != 0) {
//            string.replace("我是密文：", "");
        }
        return sm2EncUtils(string);
    }

    public static String sm2EncUtils(String data) {

        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt512UqWL9CUTRQ091xtI1EcNIrWspYZmv8PJPS0m7aTarVBtZb4vJpHvVAmPsZJ+b12PfgZOeubOEHD2MEHW2/HE+wlz1oFtHEAmJAJiLsnpKIeeW1bV02vc1tJ6qcVybPNqjXE6WLO3tJnS7sWSJ9GHiaJ1iSQoTjmhmkZAbmwIDAQAB";

        RSA rsa = new RSA(null,publicKeyStr);
        //公钥加密，私钥解密
        LogUtils.e("密文---->"+data);
//        byte[] decrypt2 = rsa.decrypt(HexUtil.decodeHex(data), KeyType.PublicKey);
        byte[] decrypt2 = EncryptUtils.decryptRSA(HexUtil.decodeHex(data),Base64.decode(publicKeyStr),true,"RSA/ECB/PKCS1Padding");
        try {

            System.out.println("解密----->"+StrUtil.str(decrypt2,CharsetUtil.CHARSET_UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StrUtil.str(decrypt2,CharsetUtil.CHARSET_UTF_8);
    }
}
