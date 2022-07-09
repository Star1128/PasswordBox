package com.ethan.passwordbox.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * NOTE:
 *
 * @author Ethan
 */
@Entity
public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private long id;
    private final String appName;
    private final int imageId;
    private final String userName;
    private String password;

    public Item(String appName, String userName, int imageId, String password) {
        this.appName = appName;
        this.userName = userName;
        this.imageId = imageId;
        this.password = password;
    }

    protected Item(Parcel in) {
        appName = in.readString();
        imageId = in.readInt();
        userName = in.readString();
        id = in.readLong();
        password = in.readString();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeInt(imageId);
        dest.writeString(userName);
        dest.writeLong(id);
        dest.writeString(password);
    }
}
