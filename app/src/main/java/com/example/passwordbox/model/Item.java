package com.example.passwordbox.model;

/**
 * NOTE:
 *
 * @author wxc 2021/8/10
 * @version 1.0.0
 */
public class Item {

    private final String appName;
    private final int imageId;

    public Item(String appName, int imageId) {
        this.appName = appName;
        this.imageId = imageId;
    }

    public String getAppName() {
        return appName;
    }

    public int getImageId() {
        return imageId;
    }
}
