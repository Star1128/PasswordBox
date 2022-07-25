package com.ethan.passwordbox.utils;

import com.ethan.passwordbox.R;
import com.ethan.passwordbox.config.Cons;

/**
 * NOTE:
 *
 * @author Ethan 2022/7/13
 */
public class IdUtil {
    public static int importanceId2ImageId(int importanceId) {
        int imageId;
        switch (importanceId) {
            case Cons.Importance.MOST:
                imageId = R.drawable.key_red_v2;
                break;
            case Cons.Importance.MORE:
                imageId = R.drawable.key_orange_v2;
                break;
            case Cons.Importance.NORMAL:
                imageId = R.drawable.key_blue_v2;
                break;
            default:
                imageId = R.drawable.key_default;
        }
        return imageId;
    }
}
