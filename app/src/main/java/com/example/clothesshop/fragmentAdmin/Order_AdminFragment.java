package com.example.clothesshop.fragmentAdmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.OrderActivity;
import com.example.clothesshop.adapter.ViewpaperOrderAdapter;
import com.example.clothesshop.adapteradmin.ViewpaperAdminAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Order_AdminFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewpaperAdminAdapter adapter;
    public Order_AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order__admin, container, false);
        tabLayout = view.findViewById(R.id.tablayout_orderAdmin);
        viewPager2 = view.findViewById(R.id.viewpaper_orderadmin);
        adapter = new ViewpaperAdminAdapter(getActivity());
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
        return view;
    }
}