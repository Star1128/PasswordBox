package com.ethan.passwordbox.data.local;

import android.content.Context;

import androidx.room.*;

import com.ethan.passwordbox.POJO.Item;

/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
@Database(version = 1, entities = {Item.class})
public abstract class AppRoomDatabase extends RoomDatabase {
    // 懒汉式单例
    private static AppRoomDatabase mMyRoomDatabase = null;

    public static AppRoomDatabase getMyRoomDatabase(Context context) {
        if (mMyRoomDatabase == null) {
            mMyRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,
                            "PasswordBox")
                    .build();
        }
        return mMyRoomDatabase;
    }

    public abstract AppDao appDao();
}
