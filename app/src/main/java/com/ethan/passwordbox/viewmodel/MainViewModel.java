package com.ethan.passwordbox.viewmodel;

import androidx.lifecycle.*;

import com.ethan.ethanutils.ELog;
import com.ethan.passwordbox.data.local.AppDao;
import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.data.local.AppRoomDatabase;
import com.ethan.passwordbox.POJO.Item;

import java.util.List;

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
            mList.postValue(appDao.queryAllOrderBy());
            ELog.d("从数据库中获取了List");
        }).start();
    }
}
