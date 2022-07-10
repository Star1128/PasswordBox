package com.ethan.passwordbox.config;

/**
 * NOTE:
 *
 * @author Ethan 2022/7/10
 */
public class Cons {
    public static class Importance {
        public static final int MOST = 1;
        public static final int MORE = 2;
        public static final int NORMAL = 3;
    }

    public static class Time{
        // 长按Item 密码展示时间
        public static final int PSW_SHOW_TIME=1000;
    }

    public static class Encrypt{
        public static final String PRIVATE_KEY="2000w11w28x17c05";
        public static final String IV_PARAMETER="138e0321t6h3a7n7";
    }
}
