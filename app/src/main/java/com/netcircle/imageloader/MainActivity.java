package com.netcircle.imageloader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private String path = "https://api.dribbble.com/v1/shots?sort=recent&page=1&per_page=30";
    private String token = "a62b88ea291c0d0e5b9295fdb8930936f945027bb84ff747ef6b89f8a9cd4da1";


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private GridLayoutManager mLayoutManager;
    private int pages=1;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    ArrayList<ListImageItem> imageItemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getVolleyData();

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
                imageItemList.clear();
                doGet();
                break;
            case BOTTOM_REFRESH:
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

                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                Gson gson = new Gson();
                for (JsonElement user : jsonArray) {
                    ImageJsonBean imageJsonBean = gson.fromJson(user, ImageJsonBean.class);
                    ImagesBean imagesBean = imageJsonBean.getImages();
                    String imageUrl = imagesBean.getTeaser();
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


}
