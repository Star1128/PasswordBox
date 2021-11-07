package com.example.passwordbox.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.passwordbox.*;
import com.example.passwordbox.databinding.ActivityMainBinding;
import com.example.passwordbox.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Item是用于展示的,只有应用名和图标
 * list是真正存储数据的
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    List<Item> lis;
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("应用列表");

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        pref = getSharedPreferences("info", MODE_PRIVATE);
        mEditor = pref.edit();
        Gson gson = new Gson();//Gson对象
        String js = pref.getString("json", "default");//从内存中得到json串
        if (js.equals("default")) {//如果内存中没有，那么就是第一次启动
            lis = new ArrayList<>();//新建一个空list
            mEditor.putString("json", gson.toJson(lis));//空list->json再放回内存
            mEditor.apply();//提交
        } else {//如果内存有json串，取出来传它
            lis = gson.fromJson(js, new TypeToken<List<Item>>() {
            }.getType());//把json串转化为List
        }
        ItemAdapter adapter = new ItemAdapter(lis);
        mBinding.recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_main, menu);//inflate膨胀
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
        return true;
    }
}