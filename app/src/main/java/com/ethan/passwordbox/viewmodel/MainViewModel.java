package com.ethan.passwordbox.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.ethan.ethanutils.log.ELog;
import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.core.ThreadPoolCore;
import com.ethan.passwordbox.data.local.db.AppDao;
import com.ethan.passwordbox.data.local.db.AppRoomDatabase;
import com.ethan.passwordbox.data.local.sp.SPManager;
import com.ethan.passwordbox.pojo.Item;
import com.ethan.passwordbox.pojo.MatchingItem;
import com.ethan.passwordbox.preferences.CustomMatching;

import java.util.List;

/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
public class MainViewModel extends ViewModel implements LifecycleObserver {

    private final MutableLiveData<List<MatchingItem>> mMatchingList = new MutableLiveData<>();
    private MutableLiveData<List<Item>> mList = new MutableLiveData<>();

    public MutableLiveData<List<MatchingItem>> getMatchingList() {
        return mMatchingList;
    }

    public LiveData<List<Item>> getList() {
        return mList;
    }

    public void setList(MutableLiveData<List<Item>> list) {
        mList = list;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void loadFromDB() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AppDao appDao = AppRoomDatabase.getMyRoomDatabase(MainApplication.mContext).appDao();
                List<Item> items = appDao.queryAllOrderBy();
                // TODO: 2022/9/3 拼音排序暂时没有解决方案
                mList.postValue(items);
                ELog.d("从数据库中获取了List");
            }
        };
        ThreadPoolCore.getThreadPool().execute(runnable);
    }

    public void saveToLocal(String key, String value) {
        SPManager.saveAsync(key, value);

        // 保证使用时生效，需要即时更新 Map
        CustomMatching.registerMatching(key, value);
        // 保证 UI 生效，需要即时更新 List
        if (mMatchingList.getValue() != null) {
            mMatchingList.getValue().add(new MatchingItem(key, value));
        }
    }

    public void loadCustomMatching() {
        SPManager.loadCustomMatching(new CustomMatching.loadMatchingListener() {
            @Override
            public void onSuccess(List<MatchingItem> list) {
                mMatchingList.postValue(list);
            }
        });
    }
}
