package com.netcircle.imageloader.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sweetgirl on 2017/11/21
 */

public class VolleyRequest {

    public static StringRequest stringRequest;
    public Context context;

    public static class MyStringRequest extends StringRequest {
        public Map<String, String> headers = new HashMap<String, String>();

        public MyStringRequest(int method, String url,
                               Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            // TODO Auto-generated method stub
            return headers;
        }

    }
}
