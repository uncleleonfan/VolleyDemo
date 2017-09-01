package com.itheima.volleydemo;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Leon on 2017/2/12.
 */

public class GsonRequest<T> extends JsonRequest<T> {

    private Gson mGson = new Gson();

    private Class<T> mClass;//要解析的类的类对象

    public GsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

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
            parsedString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsedString = new String(response.data);
        }
        Log.d(TAG, "parsedString: " + parsedString);
        T result = mGson.fromJson(parsedString, mClass);
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }
}
