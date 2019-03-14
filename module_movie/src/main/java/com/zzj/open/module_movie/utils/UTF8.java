package com.zzj.open.module_movie.utils;



import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/20 14:04
 * @desc :
 * @version: 1.0
 */
public class UTF8 {
    /**
     * Utf8URL编码
     * @param
     * @return
     */
    public static final String Utf8URLencode(String text) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 0 && c <= 255) {
                result.append(c);
            }else {
                byte[] b = new byte[0];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                }catch (Exception ex) {
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return result.toString();
    }
    /**
     * Utf8URL解码
     * @param text
     * @return
     */
    public static final String Utf8URLdecode(String text) {
        String result = "";
        int p = 0;
        if (text!=null && text.length()>0){
            text = text.toLowerCase();
            p = text.indexOf("%e");
            if (p == -1) return text;
            while (p != -1) {
                result += text.substring(0, p);
                text = text.substring(p, text.length());
                if (text == "" || text.length() < 9) return result;

                result += CodeToWord(text.substring(0, 9));
                text = text.substring(9, text.length());
                p = text.indexOf("%e");
            }
        }
        return result + text;
    }
    /**
     * utf8URL编码转字符
     * @param text
     * @return
     */
    private static final String CodeToWord(String text) {
        String result;
        if (Utf8codeCheck(text)) {
            byte[] code = new byte[3];
            code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
            code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
            code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
            try {
                result = new String(code, "UTF-8");
            }catch (UnsupportedEncodingException ex) {
                result = null;
            }
        }
        else {
            result = text;
        }
        return result;
    }
    /**
     * 编码是否有效
     * @param text
     * @return
     */
    private static final boolean Utf8codeCheck(String text){
        String sign = "";
        if (text.startsWith("%e"))
            for (int i = 0, p = 0; p != -1; i++) {
                p = text.indexOf("%", p);
                if (p != -1)
                    p++;
                sign += p;
            }
        return sign.equals("147-1");
    }
    /**
     * 判断是否Utf8Url编码
     * @param text
     * @return
     */
    public static final boolean isUtf8Url(String text) {
        text = text.toLowerCase();
        int p = text.indexOf("%");
        if (p != -1 && text.length() - p > 9) {
            text = text.substring(p, p + 9);
        }
        return Utf8codeCheck(text);
    }
    /**
     * 测试
     * @param args
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        String url;
        url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
        url = new String(java.util.Base64.getDecoder().decode("bWFjX3VybD11bmVzY2FwZSgnJXU2ZTA1JXU2NjcwJXU3MjQ4JTI0aHR0cHMlM0ElMkYlMkZjc3ZjLnFuc2RrLmNvbSUyRnByb2plY3QlMkY5Njk2YTc2ZC0wZTYwLTQxNGUtYTA2Ni02Y2Q1Yzg3MjFhNmElMkZBVCV1NTg1NCV1ZmYxYVoldTY1OTcldTU5MjkldTRmN2YuSEMldTdjYmUldTY4MjEldTRlMmQldTViNTcldTcyNDgubXA0Jyk7"));
//        if(isUtf8Url(url)){

//            url = URLDecoder.decode(url,"GBK");

        System.out.println(url);
        try {
            System.out.println(URLDecoder.decode("https%3A%2F%2Fcsvc.qnsdk.com%2Fproject%2F9696a76d-0e60-414e-a066-6cd5c8721a6a%2FAT%u5854%uff1aZ%u6597%u5929%u4f7f.HC%u7cbe%u6821%u4e2d%u5b57%u7248.mp4","utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        }
        url = "http://www.google.com/search?hl=zh-cn&newwindow=1&q=中国大百科在线全文检索&btng=搜索&lr=";
        if(!isUtf8Url(url)){
            System.out.println(Utf8URLencode(url));
        }
    }
}
