package com.ethan.ethanutils;

/**
 * NOTE:
 *
 * @author wxc 2021/11/22
 * @version 1.0.0
 */
public class MemoryUtil {

    public static String getRunTimeMemory(){
        return "系统给当前应用分配的运存大小: "+Runtime.getRuntime().maxMemory()+"\n当前应用占用的运存大小: "+Runtime.getRuntime().totalMemory();
    }
}
