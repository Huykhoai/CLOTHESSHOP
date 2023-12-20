package com.example.clothesshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.ViewpaperAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {
    ViewpaperAdapter adapter;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_paper);
        adapter = new ViewpaperAdapter(LoginActivity.this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout,viewPager2,  new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: tab.setText("Đăng Nhập");break;
                    case 1: tab.setText("Đăng kí"); break;
                    default:break;
                }
            }
        });
        mediator.attach();
        tabLayout.setTranslationY(-500);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(1000).start();
    }
}