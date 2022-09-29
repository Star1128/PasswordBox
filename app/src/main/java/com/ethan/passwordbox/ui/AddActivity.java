package com.ethan.passwordbox.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ethan.ethanutils.ui.EToast;
import com.ethan.passwordbox.R;
import com.ethan.passwordbox.data.local.db.DBManager;
import com.ethan.passwordbox.databinding.ActivityAddBinding;
import com.ethan.passwordbox.encrypt.AES;
import com.ethan.passwordbox.pojo.Item;
import com.ethan.passwordbox.preferences.CustomMatching;

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

    private void readInput() {
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
            readInput();

            if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password) && radioButtonId != -1) {
                // 尝试进行映射匹配，匹配成功就替换
                String userNameAct = CustomMatching.tryMatching(userName);
                if (!TextUtils.isEmpty(userNameAct)) {
                    userName = userNameAct;
                }
                String passwordAct = CustomMatching.tryMatching(password);
                if (!TextUtils.isEmpty(passwordAct)) {
                    password = passwordAct;
                }
                // 密码加密
                String psw_encrypt = AES.encrypt_AES(password);
                Item newItem = new Item(appName, userName, radio2ImportanceId(radioButtonId), psw_encrypt, getString(R.string.version_code));
                saveToDatabase(newItem);
                finish();
            } else {
                EToast.showToast("请输入完整信息");
            }
        }
        return true;
    }

    private void saveToDatabase(Item item) {
        DBManager.insertItem(item);
    }
}