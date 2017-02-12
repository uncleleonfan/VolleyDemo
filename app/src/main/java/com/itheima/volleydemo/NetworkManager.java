package com.itheima.volleydemo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Leon on 2017/2/12.
 */

public class NetworkManager {

    private static NetworkManager sNetworkManager;

    private RequestQueue mQueue;


    public static NetworkManager getInstance() {
        if (sNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (sNetworkManager == null) {
                    sNetworkManager = new NetworkManager();
                }
            }
        }
        return sNetworkManager;
    }

    public void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public void sendRequest(Request request) {
        mQueue.add(request);
    }
}
