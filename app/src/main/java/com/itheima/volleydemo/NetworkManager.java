package com.itheima.volleydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Leon on 2017/2/12.
 */

public class NetworkManager {

    private static NetworkManager sNetworkManager;

    private RequestQueue mQueue;

    private ImageLoader mImageLoader;

    private static final int DEFAULT_IMAGE_CACHE_SIZE = 4 * 1024 * 1024;//4M


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
        mImageLoader = new ImageLoader(mQueue, new ImageLruCache(DEFAULT_IMAGE_CACHE_SIZE));
    }

    public void sendRequest(Request request) {
        mQueue.add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 图片内存LRU缓存
     */
    private static class ImageLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{


        public ImageLruCache(int maxSize) {
            super(maxSize);
        }

        /**
         * 返回对应key的bitmap的大小，当存入缓存时，要计算是否超出缓存的最大值
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        /**
         * 返回对应url的图片缓存
         */
        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        /**
         * 存入缓存
         */
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
