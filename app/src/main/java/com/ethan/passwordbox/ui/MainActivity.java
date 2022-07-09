package com.ethan.passwordbox.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ethan.passwordbox.R;
import com.ethan.passwordbox.databinding.ActivityMainBinding;
import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.viewmodel.MainViewModel;
import com.ethan.passwordbox.viewmodel.ViewModelFactory;

import java.util.List;

/**
 * Item是用于展示的,只有应用名和图标
 * list是真正存储数据的
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("应用列表");
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                ItemAdapter adapter = new ItemAdapter(items);
                mBinding.recyclerview.setAdapter(adapter);
            }
        });

        getLifecycle().addObserver(mainViewModel);

        // 设置加载圈的颜色,多个颜色就是每转一圈换一个颜色
        mBinding.swipe.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.loadFromDB();
                mBinding.swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_main, menu);
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