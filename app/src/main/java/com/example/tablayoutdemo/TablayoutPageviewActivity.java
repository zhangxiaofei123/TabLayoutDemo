package com.example.tablayoutdemo;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.TablayoutPageviewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import fragment.TablayoutPageviewFragment;

public class TablayoutPageviewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        titles.add("上海");
        titles.add("头条推荐");
        titles.add("头条推荐");
        titles.add("头条推荐");
        titles.add("头条推荐");

        for (int i = 0; i < titles.size(); i++) {

            fragments.add(i, TablayoutPageviewFragment.newInstance(titles.get(i), titles.get(i)));

        }

        viewPager.setAdapter(new TablayoutPageviewAdapter(getSupportFragmentManager(), fragments, titles));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}