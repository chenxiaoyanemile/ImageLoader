package com.netcircle.imageloader.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.netcircle.imageloader.gen.DaoMaster;
import com.netcircle.imageloader.gen.DaoSession;
import com.netcircle.imageloader.gen.UserDao;


public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
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
