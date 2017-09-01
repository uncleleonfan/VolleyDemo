package com.itheima.volleydemo;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Leon on 2017/2/12.
 */

public class GsonRequest<T> extends JsonRequest<T> {

    private Gson mGson = new Gson();

    private Class<T> mClass;//要解析的类的类对象

    public GsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    /**
     *  GET请求
     * @param beanClass 网络解析的Java Bean类的Class对象
     * @param url 网络地址
     * @param listener 成功和失败的回调
     */
    public GsonRequest(Class<T> beanClass, String url, NetworkListener<T> listener) {
        this(Method.GET, url, null, listener, listener);
        mClass = beanClass;
    }

    /**
     * 将网络请求的结果用Gson解析成期望的类型，在子线程中调用
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsedString;
        try {
            //将网络响应的字节数组转换成字符串
            parsedString = new String(response.data, PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            parsedString = new String(response.data);
        }
        //将字符串转换成java bean
        T result = mGson.fromJson(parsedString, mClass);
        //返回解析后的结果，使用Response对象包装
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }
}
