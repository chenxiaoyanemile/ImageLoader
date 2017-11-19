package com.netcircle.imageloader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.netcircle.imageloader.adapter.MyRecyclerViewAdapter;
import com.netcircle.imageloader.model.ListImageItem;
import com.netcircle.imageloader.util.EndLessOnScrollListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String path = "https://api.dribbble.com/v1/shots?sort=recent&page=1&per_page=30";
    private String token = "a62b88ea291c0d0e5b9295fdb8930936f945027bb84ff747ef6b89f8a9cd4da1";
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    String result;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private GridLayoutManager mLayoutManager;
    private int pages=1;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    ArrayList<ListImageItem> imageItemList = new ArrayList<>();

    private String[] urlArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        doGet();

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

                    getData(token,path);

                    parseData(result);

                    getImageURL(urlArray);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return false;
    }

    /**
     * Get data
     * @param token token
     * @param url url
     * @throws Exception
     */
    public void getData(String token, String url) throws Exception {

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .url(url)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        result = response.body().string();
    }

    /**
     * parse data
     * @param jsonData json data
     * @return  list
     */
    public void parseData(String jsonData){
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonData);
            JSONArray array =(JSONArray) jsonTokener.nextValue();
            urlArray = new String[array.length()];
            for (int i =0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                JSONObject images = object.getJSONObject("images");
                String teaser = images.getString("teaser");
                urlArray[i] = teaser;
                Log.i("parseData","teaser"+urlArray[i]+i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * get url
     * @param arr image url array
     * @return
     */
    public List<ListImageItem> getImageURL(String arr[]){
        for (int i = 0; i < arr.length - 6; i = i+3){
            Log.i("getImageURL","arr"+arr[i]+arr[i+1]+arr[i+3]);
            ListImageItem listImageItem = new ListImageItem(arr[i],arr[i+1],arr[i+2]);
            imageItemList.add(listImageItem);
        }
        return imageItemList;
    }
}
