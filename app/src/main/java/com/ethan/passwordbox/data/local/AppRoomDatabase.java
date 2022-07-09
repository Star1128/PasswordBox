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
    //    static Migration mMigration_1_2 = new Migration(1, 2) {
    //        @Override
    //        public void migrate(@NonNull SupportSQLiteDatabase database) {
    //            database.beginTransaction();
    //            database.execSQL("CREATE TABLE ItemV1 (id INTEGER PRIMARY KEY AUTOINCREMENT not null, appName TEXT, importanceId INTEGER not null default \"null\", userName TEXT, password TEXT)");
    //            database.execSQL("INSERT INTO ItemV1(id,appName,userName,password) SELECT id,appName,userName,password FROM Item;");
    //            database.execSQL("DROP TABLE Item");
    //            database.execSQL("ALTER TABLE ItemV1 RENAME TO Item");
    //            database.endTransaction();
    //        }
    //    };
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
