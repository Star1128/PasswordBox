package com.ethan.ethanutils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * NOTE:
 *
 * @author wxc 2021/11/22
 * @version 1.0.0
 */
public class KeyboardUtil {

    public static void closeKeyboard(Activity activity){
        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
        }
    }
}
