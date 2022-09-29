package com.ethan.passwordbox.preferences;

import androidx.annotation.Nullable;

import com.ethan.ethanutils.log.ELog;
import com.ethan.passwordbox.pojo.MatchingItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NOTE:
 *
 * @author Ethan 2022/9/3
 */
public class CustomMatching {

    private static final ConcurrentHashMap<String, String> customMatchMap = new ConcurrentHashMap<>();

    public static void registerAllMatching(Map<String, ?> map, loadMatchingListener listener) {
        if (map == null) {
            ELog.w("解析 SP 失败");
            return;
        }
        List<MatchingItem> list = new ArrayList<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            customMatchMap.put(entry.getKey(), entry.getValue().toString());
            list.add(new MatchingItem(entry.getKey(), entry.getValue().toString()));
        }

        listener.onSuccess(list);
    }

    public static void registerMatching(String key, String value) {
        customMatchMap.put(key, value);
    }

    public static void unRegisterMatching(String key) {
        customMatchMap.remove(key);
    }

    @Nullable
    public static String tryMatching(String key) {
        return customMatchMap.get(key);
    }

    public interface loadMatchingListener {
        void onSuccess(List<MatchingItem> list);
    }
}
