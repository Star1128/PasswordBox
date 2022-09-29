package com.ethan.passwordbox.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ethan.ethanutils.ui.EToast;
import com.ethan.passwordbox.R;
import com.ethan.passwordbox.databinding.ActivityMainBinding;
import com.ethan.passwordbox.pojo.Item;
import com.ethan.passwordbox.pojo.MatchingItem;
import com.ethan.passwordbox.ui.adapter.ItemAdapter;
import com.ethan.passwordbox.ui.adapter.MatchingItemAdapter;
import com.ethan.passwordbox.viewmodel.MainViewModel;
import com.ethan.passwordbox.viewmodel.ViewModelFactory;

import java.util.List;

/**
 * Item是用于展示的,只有应用名和图标
 * list是真正存储数据的
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    MainViewModel mMainViewModel;
    MatchingItemAdapter mMatchingItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("应用列表");
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        mMainViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(MainViewModel.class);
        mMainViewModel.getList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                ItemAdapter adapter = new ItemAdapter(items);
                mBinding.recyclerview.setAdapter(adapter);
            }
        });
        mMainViewModel.getMatchingList().observe(this, new Observer<List<MatchingItem>>() {
            @Override
            public void onChanged(List<MatchingItem> list) {
                mMatchingItemAdapter = new MatchingItemAdapter(list);
            }
        });

        // ViewModel使用 LifecycleObserver 监听生命周期
        getLifecycle().addObserver(mMainViewModel);
        // 加载自定义映射
        mMainViewModel.loadCustomMatching();

        // 设置加载圈的颜色，多个颜色就是每转一圈换一个颜色
        mBinding.swipe.setColorSchemeColors(Color.BLACK);
        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMainViewModel.loadFromDB();
                mBinding.swipe.setRefreshing(false);
            }
        });

        // 暂时不开启 FAB
        // mBinding.fabAdd.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         PopupMenu popupMenu = new PopupMenu(MainActivity.this, mBinding.fabAdd);
        //         popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        //         popupMenu.show();
        //      }
        // });
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
        } else if (item.getItemId() == R.id.item_add_custom_config) {
            createMatchingDialog();
        }
        return true;
    }

    private void createMatchingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_add_matching, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.rv_matching);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMatchingItemAdapter);

        dialog.setView(contentView);
        dialog.setTitle("创建映射规则");
        dialog.setIcon(R.drawable.add_matching);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText_key = contentView.findViewById(R.id.et_matching_key);
                EditText editText_value = contentView.findViewById(R.id.et_matching_value);
                String key = editText_key.getText().toString();
                String value = editText_value.getText().toString();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    mMainViewModel.saveToLocal(key, value);
                    EToast.showToast("保存成功");
                } else {
                    EToast.showToast("请输入完整信息");
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
        // 先 show() 再调节按钮颜色
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }
}