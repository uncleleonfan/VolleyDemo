package com.itheima.volleydemo;

import android.app.Application;

/**
 * Created by Leon on 2017/2/12.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(getApplicationContext());
    }
}
