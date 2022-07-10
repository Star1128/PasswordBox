package com.ethan.passwordbox.ui;

import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.data.local.AppDao;
import com.ethan.passwordbox.data.local.AppRoomDatabase;
import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.R;
import com.ethan.passwordbox.databinding.ActivityShowBinding;

@Deprecated
public class ShowActivity extends AppCompatActivity {
    ActivityShowBinding mBinding;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("查看密码");

        Intent intent = getIntent();
        Bundle info = intent.getBundleExtra("info");
        item = info.getParcelable("app");

        mBinding.activityShowAppName.setText(mBinding.activityShowAppName.getText() + item.getAppName());
        mBinding.activityShowUserName.setText(mBinding.activityShowUserName.getText() + item.getUserName());
        mBinding.activityShowPassword.setText(mBinding.activityShowPassword.getText() + item.getPassword());
    }

    public void deleteItem() {
        new Thread(() -> {
            AppDao appDao = AppRoomDatabase.getMyRoomDatabase(MainApplication.mContext).appDao();
            appDao.deleteItem(item);
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("删除确认");
            builder.setMessage("删除后不可恢复，是否继续？");
            builder.setCancelable(true); // 使用back键是否可以取消该对话框
            builder.setPositiveButton("继续", (DialogInterface dialogInterface, int which) -> {
                deleteItem();
                finish();
            });
            builder.setNegativeButton("再想想", (DialogInterface dialogInterface, int which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // 设置按钮颜色
            dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        }
        return true;
    }
}