package com.itheima.volleydemo;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class NetworkListener<T> implements Response.Listener<T>, Response.ErrorListener{
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
