package com.example.passwordbox.ui;

import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.passwordbox.R;
import com.example.passwordbox.databinding.ActivityShowBinding;
import com.example.passwordbox.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    ActivityShowBinding mBinding;
    TextView tAppName;
    TextView tUserName;
    TextView tPassword;
    ImageButton mImageButton;

    String appName;
    String userName;
    String password;

    SharedPreferences pref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("查看密码");

        Intent intent = getIntent();//适配器传进来的intent
        appName = intent.getStringExtra("item");
        pref = getSharedPreferences("info", MODE_PRIVATE);

        userName = pref.getString(appName + "userName", "default");//类似于getOrDefault()
        password = pref.getString(appName + "password", "default");

        mBinding.activityShowAppName.setText(mBinding.activityShowAppName.getText() + appName);
        mBinding.activityShowUserName.setText(mBinding.activityShowUserName.getText() + userName);
        mBinding.activityShowPassword.setText(mBinding.activityShowPassword.getText() + password);
    }

    public void deleteItemByAppName(String appName) {
        Gson gson = new Gson();//Gson对象
        String js = pref.getString("json", "default");//从内存中得到json串
        List<Item> lis = gson.fromJson(js, new TypeToken<List<Item>>() {
        }.getType());//把json串转化为List
        for (Item m : lis) {
            if (m.getAppName().equals(appName)) {
                lis.remove(m);
                mEditor.putString("json", gson.toJson(lis));//list->json再放回内存
                mEditor.apply();//提交
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_show, menu);//inflate膨胀
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_delete) {
            mEditor = pref.edit();//得到编辑器
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("删除确认");
            builder.setMessage("删除后不可恢复，是否继续？");
            builder.setCancelable(true);//使用back键是否可以取消该对话框
            builder.setPositiveButton("继续", (DialogInterface dialogInterface, int which) -> {
                mEditor.remove(appName);//清除数据
                mEditor.remove(appName + "userName");
                mEditor.remove(appName + "password");
                mEditor.apply();
                deleteItemByAppName(appName);//删除前台list展示中的对象
                finish();//退回前台页面
            });
            builder.setNegativeButton("再想想", (DialogInterface dialogInterface, int which) -> { });

            AlertDialog dialog=builder.create();
            dialog.show();
            //改变按钮颜色
            dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        }
        return true;
    }
}