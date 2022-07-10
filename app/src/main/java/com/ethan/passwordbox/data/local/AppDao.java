package com.ethan.passwordbox.data.local;

import androidx.room.*;

import com.ethan.passwordbox.POJO.Item;

import java.util.List;

/**
 * NOTE:
 *
 * @author Ethan 2022/6/19
 */
@Dao
public interface AppDao {
    @Insert
    long insertItem(Item item);

    @Query("select * from Item")
    List<Item> queryAll();

    @Query("select * from Item order by importanceId, appName collate nocase asc")
    List<Item> queryAllOrderBy();

    @Delete
    void deleteItem(Item item);
}
