package com.example.passwordbox;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_show);
        initView();

        Intent intent = getIntent();//适配器传进来的intent
        appName = intent.getStringExtra("item");
        pref = getSharedPreferences("info", MODE_PRIVATE);

        userName = pref.getString(appName + "userName", "default");//类似于getOrDefault()
        password = pref.getString(appName + "password", "default");

        tAppName.setText(tAppName.getText()+appName);
        tUserName.setText(tUserName.getText()+userName);
        tPassword.setText(tPassword.getText()+password);
        onClickDelete();
    }

    private void initView() {
        tAppName = findViewById(R.id.activity_show_appName);
        tUserName = findViewById(R.id.activity_show_userName);
        tPassword = findViewById(R.id.activity_show_password);
        mImageButton=findViewById(R.id.delete);
    }

    private void onClickDelete(){
        mEditor=pref.edit();//得到编辑器
        mImageButton.setOnClickListener((View v)->{
            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setTitle("删除确认");
            dialog.setMessage("删除后不可恢复，是否继续？");
            dialog.setCancelable(true);//使用back键是否可以取消该对话框
            dialog.setPositiveButton("继续",(DialogInterface dialogInterface, int which)->{
                mEditor.remove(appName);//清除数据
                mEditor.remove(appName + "userName");
                mEditor.remove(appName + "password");
                mEditor.apply();
                deleteItemByAppName(appName);//删除前台list展示中的对象
                finish();//退回前台页面
            });
            dialog.setNegativeButton("再想想",(DialogInterface dialogInterface,int which)->{ });
            dialog.show();
        });
    }

    public void deleteItemByAppName(String appName){
        Gson gson = new Gson();//Gson对象
        String js = pref.getString("json", "default");//从内存中得到json串
        List<Item> lis = gson.fromJson(js, new TypeToken<List<Item>>() { }.getType());//把json串转化为List
        for (Item m : lis) {
            if(m.getAppName().equals(appName)){
                lis.remove(m);
                mEditor.putString("json", gson.toJson(lis));//list->json再放回内存
                mEditor.apply();//提交
                break;
            }
        }
    }
}