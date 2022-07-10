package com.ethan.passwordbox.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ethan.passwordbox.POJO.Item;
import com.ethan.passwordbox.R;
import com.ethan.passwordbox.config.MainApplication;
import com.ethan.passwordbox.data.local.AppDao;
import com.ethan.passwordbox.data.local.AppRoomDatabase;
import com.ethan.passwordbox.databinding.ActivityAddBinding;
import com.ethan.passwordbox.encrypt.AES;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding mBinding;
    String appName;
    String userName;
    String password;
    int radioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setSubtitle("添加应用");
    }

    private void read() {
        appName = mBinding.activityAddAppName.getText().toString();
        userName = mBinding.activityAddUserName.getText().toString();
        password = mBinding.activityAddPassword.getText().toString();
        radioButtonId = mBinding.radiogroup.getCheckedRadioButtonId();
    }

    private int radio2ImportanceId(int radioId) {
        int importanceId = 0;
        if (radioId == R.id.radioButton1) {
            importanceId = 1;
        } else if (radioId == R.id.radioButton2) {
            importanceId = 2;
        } else if (radioId == R.id.radioButton3) {
            importanceId = 3;
        }
        return importanceId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_commit) {
            // 读取输入
            read();

            if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password) && radioButtonId != -1) {
                String psw_encrypt = AES.encrypt_AES(password);
                Item newItem = new Item(appName, userName, radio2ImportanceId(radioButtonId), psw_encrypt, getString(R.string.version_code));
                new Thread(() -> {
                    AppDao appDao = AppRoomDatabase.getMyRoomDatabase(MainApplication.mContext).appDao();
                    newItem.setId(appDao.insertItem(newItem));
                }).start();
                finish();
            } else
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}