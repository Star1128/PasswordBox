package com.example.passwordbox.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.passwordbox.R;
import com.example.passwordbox.databinding.ActivityAddBinding;
import com.example.passwordbox.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding mBinding;

    String appName;
    String userName;
    String password;
    int radioButtonId;

    SharedPreferences pref;
    SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("添加应用");

        pref = getSharedPreferences("info", MODE_PRIVATE);
        mEditor = pref.edit();

    }

    private void read() {
        appName = mBinding.activityAddAppName.getText().toString();
        userName = mBinding.activityAddUserName.getText().toString();
        password = mBinding.activityAddPassword.getText().toString();
        radioButtonId = mBinding.group.getCheckedRadioButtonId();
    }

    private int radioButtonToImageId(int radioId) {
        int imageId = 0;
        switch (radioId) {
            case R.id.radioButton1:
                imageId = R.drawable.key_red;
                break;
            case R.id.radioButton2:
                imageId = R.drawable.key_orange;
                break;
            case R.id.radioButton3:
                imageId = R.drawable.key_blue;
                break;
        }
        return imageId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_add, menu);//inflate膨胀
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_commit) {
            read();//读取输入
            if (!(TextUtils.isEmpty(appName) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || radioButtonId == 0)) {
                Item newItem = new Item(appName, radioButtonToImageId(radioButtonId));

                Gson gson = new Gson();
                String js = pref.getString("json", "default");//从内存中得到json串
                List<Item> list = gson.fromJson(js, new TypeToken<List<Item>>() { }.getType());//json->string
                list.add(newItem);//应用名和图标的描述信息放入list
                mEditor.putString("json", gson.toJson(list));//list->json再放回内存

                mEditor.putString(appName, appName);//存入存储
                mEditor.putString(appName + "userName", userName);
                mEditor.putString(appName + "password", password);
                mEditor.apply();
                finish();//销毁这个活动
            } else {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}