package com.ethan.ethanutils;

import java.io.Closeable;
import java.io.IOException;

/**
 * NOTE:
 *
 * @author wxc 2021/11/27
 * @version 1.0.0
 */
public class IOUtil {

    /**
     * 关闭IO流,运用了多态
     * @param closeable IO流的公共接口
     */
    public static void closeStream(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
