package com.example.clothesshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.ViewpaperOrderAdapter;
import com.example.clothesshop.fragment.CancelOrderFragment;
import com.example.clothesshop.fragment.DaHuyFragment;
import com.example.clothesshop.model.Order;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
      TabLayout tabLayout;
      ViewPager2 viewPager2;
      Toolbar toolbar;
      ViewpaperOrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        tabLayout = findViewById(R.id.tablayot_order);
        viewPager2 = findViewById(R.id.viewpaper_order);
        toolbar = findViewById(R.id.toolbarOrder);
        adapter = new ViewpaperOrderAdapter(OrderActivity.this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: tab.setText("Tất cả");break;
                    case 1: tab.setText("Chờ xác nhận");break;
                    case 2: tab.setText("Xác nhận ");break;
                    case 3: tab.setText("Đã hủy");break;
                    case 4: tab.setText("Đang giao");break;
                    case 5: tab.setText("Hoàn tất");break;
                    default:tab.setText("Tất cả");break;
                }
            }
        });
        mediator.attach();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}