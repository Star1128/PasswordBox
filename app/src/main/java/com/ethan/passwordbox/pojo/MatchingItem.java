package com.ethan.passwordbox.pojo;

/**
 * @author Ethan 2022/9/28
 */
public class MatchingItem {

    private String key;
    private String value;

    public MatchingItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
