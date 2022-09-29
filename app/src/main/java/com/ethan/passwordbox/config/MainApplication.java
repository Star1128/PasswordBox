package com.ethan.passwordbox.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.ethan.ethanutils.log.ELog;
import com.ethan.ethanutils.ui.EToast;
import com.ethan.passwordbox.core.ThreadPoolCore;
import com.ethan.passwordbox.data.local.db.DBManager;
import com.ethan.passwordbox.data.local.sp.SPManager;


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
        SPManager.init(getSharedPreferences(Cons.Custom.CUSTOM_CONFIG_TABLE, MODE_PRIVATE));
        DBManager.init();
        ELog.init("Ethan");
        ThreadPoolCore.init();
        EToast.init(mContext);
    }
}
