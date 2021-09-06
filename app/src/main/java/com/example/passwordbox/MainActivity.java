package com.example.passwordbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> lis;
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;
    RecyclerView recyclerView;
    ImageButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


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
            lis = gson.fromJson(js, new TypeToken<List<Item>>() { }.getType());//把json串转化为List
        }
        ItemAdapter adapter = new ItemAdapter(lis);
        recyclerView.setAdapter(adapter);

        mButton = findViewById(R.id.menu);
        mButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
    }
}