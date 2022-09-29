package com.ethan.passwordbox.data.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ethan.passwordbox.pojo.Item;

/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
@Database(version = 2, entities = {Item.class})
public abstract class AppRoomDatabase extends RoomDatabase {
        static Migration mMigration_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("alter table Item add column version text default '2.0'");
            }
        };
    // 懒汉式单例
    private static AppRoomDatabase mMyRoomDatabase = null;

    public static AppRoomDatabase getMyRoomDatabase(Context context) {
        if (mMyRoomDatabase == null) {
            mMyRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,
                            "PasswordBox")
                    .addMigrations(mMigration_1_2)
                    .build();
        }
        return mMyRoomDatabase;
    }

    public abstract AppDao appDao();
}
