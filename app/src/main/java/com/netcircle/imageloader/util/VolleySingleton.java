package com.netcircle.imageloader.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;

/**
 * Created by sweetgirl on 2017/11/2
 */

public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private com.android.volley.toolbox.ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private VolleySingleton(Context context){
        mRequestQueue= Volley.newRequestQueue(context);
        mImageLoader=new com.android.volley.toolbox.ImageLoader(mRequestQueue,new com.android.volley.toolbox.ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
    public static VolleySingleton getInstance(Context context){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton(context);
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
    public com.android.volley.toolbox.ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
