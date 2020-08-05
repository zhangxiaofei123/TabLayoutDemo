package com.example.tablayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.RecyclerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initViews();
    }

    void initViews() {
        List<Map<String, String>> list = new ArrayList<>();

        String[] strings = {"tablayout"};

        for (int i = 0; i < strings.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", strings[i]);
            list.add(map);
        }
        recyclerAdapter = new RecyclerAdapter(list, MainActivity.this);
        recyclerView = findViewById(R.id.recyclerview);
        /*行布局*/
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.SetOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                System.out.println("点击：" + position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, TablayoutPageviewActivity.class));
                        break;
                }

            }
        });

    }
}