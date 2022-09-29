package com.ethan.passwordbox.core;

import com.ethan.ethanutils.log.ELog;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * NOTE:
 *
 * @author Ethan 2022/9/4
 */
public class ThreadPoolCore {

    private static ThreadPoolExecutor mThreadPoolExecutor;

    public static void init() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                1,
                3,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(3));
    }

    public static ThreadPoolExecutor getThreadPool() {
        if (mThreadPoolExecutor == null) {
            ELog.w("线程池未初始化");
            init();
        }
        return mThreadPoolExecutor;
    }
}
