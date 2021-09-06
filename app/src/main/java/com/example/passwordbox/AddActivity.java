package com.example.passwordbox;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText eAppName;
    EditText eUserName;
    EditText ePassword;
    RadioGroup rGroup;
    RadioButton radioButton;
    Button commit;

    String appName;
    String userName;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = pref.edit();

        initView();

        rGroup.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {//单选按钮们
            radioButton = findViewById(rGroup.getCheckedRadioButtonId());
        });

        commit.setOnClickListener((View v) -> {
            read();//读取输入
            if (!(appName.equals("") || userName.equals("") || password.equals(""))) {
                Item item = new Item(appName, radioButtonToImageId(radioButton.getId()));

                Gson gson = new Gson();
                String js = pref.getString("json", "default");//从内存中得到json串
                List<Item> list = gson.fromJson(js, new TypeToken<List<Item>>() {
                }.getType());//json->string
                list.add(item);//应用名和图标的描述信息放入list
                mEditor.putString("json", gson.toJson(list));//list->json再放回内存

                mEditor.putString(appName, appName);//存入存储
                mEditor.putString(appName + "userName", userName);
                mEditor.putString(appName + "password", password);
                mEditor.apply();
                finish();//销毁这个活动
            } else {
                Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        eAppName = findViewById(R.id.activity_add_appName);
        eUserName = findViewById(R.id.activity_add_userName);
        ePassword = findViewById(R.id.activity_add_password);
        rGroup = findViewById(R.id.group);
        commit = findViewById(R.id.commit);
    }

    private void read() {
        appName = eAppName.getText().toString();
        userName = eUserName.getText().toString();
        password = ePassword.getText().toString();
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
}