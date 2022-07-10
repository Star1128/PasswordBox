package com.ethan.passwordbox.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.ethan.ethanutils.ELog;


/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
public class MainApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ELog.init("Ethan");
    }
}
