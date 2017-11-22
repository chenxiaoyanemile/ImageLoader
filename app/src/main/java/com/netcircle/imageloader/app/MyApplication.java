package com.netcircle.imageloader.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.netcircle.imageloader.gen.DaoMaster;
import com.netcircle.imageloader.gen.DaoSession;
import com.netcircle.imageloader.gen.UserDao;

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private UserDao mUserDao;
    public static RequestQueue queues;

    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
        instances = this;
        setDatabase();

    }

    public static MyApplication getInstances(){
        return instances;
    }


    private void setDatabase() {

        mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "UserDb", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        mUserDao = mDaoSession.getUserDao();
    }

    public static RequestQueue getHttpQueues() {
        return queues;

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public UserDao getUserDao(){
        return mUserDao;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
