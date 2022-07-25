package com.ethan.passwordbox.viewmodel;

import androidx.lifecycle.*;

import com.ethan.ethanutils.ELog;
import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.data.local.AppDao;
import com.ethan.passwordbox.data.local.AppRoomDatabase;

import java.text.Collator;
import java.util.*;

/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
public class MainViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<Item>> mList = new MutableLiveData<>();

    public LiveData<List<Item>> getList() {
        return mList;
    }

    public void setList(MutableLiveData<List<Item>> list) {
        mList = list;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void loadFromDB() {
        new Thread(() -> {
            AppDao appDao = AppRoomDatabase.getMyRoomDatabase(MainApplication.mContext).appDao();
            List<Item> items = appDao.queryAll();
            items.sort(new Comparator<Item>() {

                @Override
                public int compare(Item o1, Item o2) {
                    int result = o1.getImportanceId() - o2.getImportanceId();
                    if (result == 0) {
                        return Collator.getInstance(Locale.CHINA).compare(o1.getAppName(), o2.getAppName());
                    } else {
                        return result;
                    }
                }
            });
            mList.postValue(items);
            ELog.d("从数据库中获取了List");
        }).start();
    }
}
