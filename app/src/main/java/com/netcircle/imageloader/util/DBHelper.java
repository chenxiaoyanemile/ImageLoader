package com.netcircle.imageloader.util;

import android.content.Context;

import com.netcircle.imageloader.gen.DaoMaster;
import com.netcircle.imageloader.gen.DaoSession;

/**
 * Created by sweetgirl on 2017/11/17
 */

public class DBHelper {

    private static DBHelper instance;
    private static Context mContext;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;


    private DBHelper() {
    }

    public static void init(Context context) {
        mContext = context;
        instance = new DBHelper();
        DaoSession daoSession = getDaoSession(mContext);

    }

    public static DBHelper getInstance() {
        return instance;
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    "notes.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


}
