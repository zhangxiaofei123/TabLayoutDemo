package com.example.tablayoutdemo;

import adapter.ListViewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import view.MyListView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private MyListView listView;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initViews();
    }

    void initViews() {

        listView = findViewById(R.id.listview);
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        listViewAdapter = new ListViewAdapter(this, list);
        listView.setAdapter(listViewAdapter);
//        listViewAdapter.SetOnItemCliclLister(new ListViewAdapter.OnItemCliclLister() {
//            @Override
//            public void onClick(View view, int i) {
//                Toast.makeText(getBaseContext(),"点击了第"+i+"行",Toast.LENGTH_SHORT).show();
//            }
//        });
        listView.SetDeleteItemListener(new MyListView.DeleteItemListener() {
            @Override
            public void deleteItemClick(int i) {
                list.remove(i);
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }
}