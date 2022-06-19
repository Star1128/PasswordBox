package com.ethan.ethanutils;

import android.util.Log;

/**
 * NOTE：日志过滤器 在Application的onCreate()中init即可
 *
 * @author Ethan 2022/6/10
 */
public class ELog {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static int level;
    private static String TAG;

    public static void init(String tag) {
        TAG = tag;
        level = VERBOSE;
    }

    public static void init(String tag, int lev) {
        TAG = tag;
        level = lev;
    }

    public static void v(String msg) {
        if (level <= VERBOSE)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (level <= DEBUG)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (level <= INFO)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (level <= WARN)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (level <= ERROR)
            Log.e(TAG, msg);
    }
}
