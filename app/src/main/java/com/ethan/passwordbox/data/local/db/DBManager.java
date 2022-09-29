package com.ethan.passwordbox.data.local.db;

import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.core.ThreadPoolCore;
import com.ethan.passwordbox.pojo.Item;

/**
 * @author Ethan 2022/9/28
 */
public class DBManager {

    private static AppDao mAppDao;

    public static void init() {
        mAppDao = AppRoomDatabase.getMyRoomDatabase(MainApplication.mContext).appDao();
    }

    public static void deleteItem(Item item) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mAppDao.deleteItem(item);
            }
        };
        ThreadPoolCore.getThreadPool().execute(runnable);
    }

    public static void insertItem(Item item) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                item.setId(mAppDao.insertItem(item));
            }
        };
        ThreadPoolCore.getThreadPool().execute(runnable);
    }
}
