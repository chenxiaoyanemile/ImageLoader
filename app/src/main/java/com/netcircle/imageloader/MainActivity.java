package com.netcircle.imageloader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.netcircle.imageloader.adapter.MyRecyclerViewAdapter;
import com.netcircle.imageloader.app.MyApplication;
import com.netcircle.imageloader.model.ImageJsonBean;
import com.netcircle.imageloader.model.ImagesBean;
import com.netcircle.imageloader.model.ListImageItem;
import com.netcircle.imageloader.util.EndLessOnScrollListener;
import com.netcircle.imageloader.util.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private String path = "https://api.dribbble.com/v1/shots?sort=recent&page=1&per_page=30";
    private String token = "a62b88ea291c0d0e5b9295fdb8930936f945027bb84ff747ef6b89f8a9cd4da1";
    //private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    String result;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private GridLayoutManager mLayoutManager;
    private int pages=1;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    ArrayList<ListImageItem> imageItemList = new ArrayList<>();

    private Gson mGson;
    RequestQueue mQueue;
    StringRequest mStringRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getVolleyData();
        Log.i("获取数据","getVolleyData");

        mLayoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL, false);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        myRecyclerViewAdapter.setTextImages(imageItemList);


        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                myRecyclerViewAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });
    }

    //下啦加载
    private void loadMoreData(){
        dataOption(BOTTOM_REFRESH);
        for (int i =0; i < 10; i++){
            myRecyclerViewAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this,"加载完成",Toast.LENGTH_SHORT).show();
    }

    private void dataOption(int option){
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                imageItemList.clear();
                doGet();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                pages++;
                doGet();
                break;
        }

    }

    public void initView(){

        mRecyclerView=(RecyclerView) findViewById(R.id.new_recyclerView);
        mRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.layout_swipe_refresh);

    }

    private  boolean doGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    getVolleyData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return false;
    }


    private void getVolleyData(){

        VolleyRequest.MyStringRequest myStringRequest = new VolleyRequest.MyStringRequest(
                Request.Method.GET, path, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub

                Log.i("TAG","getVolleyData"+response);
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                Gson gson = new Gson();
                ArrayList<ImageJsonBean> imageJsonBeanArrayList = new ArrayList<>();
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    ImageJsonBean imageJsonBean = gson.fromJson(user, ImageJsonBean.class);
                    ImagesBean imagesBean = imageJsonBean.getImages();
                    String imageUrl = imagesBean.getTeaser();
                    Log.i("imageUrl","imageUrl="+imageUrl);
                    ListImageItem listImageItem = new ListImageItem(imageUrl);
                    imageItemList.add(listImageItem);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error>", error.toString());

            }
        });
        myStringRequest.headers.put("Authorization", "Bearer "+token);
        MyApplication.getHttpQueues().add(myStringRequest);
    }




/*
    *//**
     * Get data
     * @param token token
     * @param url url
     * @throws Exception
     *//*
    public void getData(String token, String url) throws Exception {

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .url(url)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        result = response.body().string();
    }

    *//**
     * parse data
     * @param jsonData json data
     * @return  list
     *//*
    public void parseData(String jsonData){
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonData);
            JSONArray array =(JSONArray) jsonTokener.nextValue();
            for (int i =0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                JSONObject images = object.getJSONObject("images");
                String teaser = images.getString("teaser");
                ListImageItem listImageItem = new ListImageItem(teaser);
                imageItemList.add(listImageItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/


}
