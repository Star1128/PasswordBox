package com.ethan.passwordbox.data.local.sp;

import android.content.SharedPreferences;

import com.ethan.passwordbox.core.ThreadPoolCore;
import com.ethan.passwordbox.preferences.CustomMatching;

/**
 * @author Ethan 2022/9/28
 */
public class SPManager {

    private static SharedPreferences mSharedPreferences;

    public static void init(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public static void loadCustomMatching(CustomMatching.loadMatchingListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CustomMatching.registerAllMatching(mSharedPreferences.getAll(), listener);
            }
        };
        ThreadPoolCore.getThreadPool().execute(runnable);
    }

    public static void saveAsync(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void deleteAsync(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
