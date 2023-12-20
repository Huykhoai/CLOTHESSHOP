package com.example.clothesshop.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clothesshop.fragment.AllOrderFragment;
import com.example.clothesshop.fragment.ChoXacnhanFragment;
import com.example.clothesshop.fragment.DaHuyFragment;
import com.example.clothesshop.fragment.DanggiaoFragment;
import com.example.clothesshop.fragment.ThanhcongFragment;
import com.example.clothesshop.fragment.XacnhanFragment;
import com.example.clothesshop.model.Order;

public class ViewpaperOrderAdapter extends FragmentStateAdapter {
    public ViewpaperOrderAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0: return new AllOrderFragment();
           case 1: return new ChoXacnhanFragment();
           case 2: return new XacnhanFragment();
           case 3: return new DaHuyFragment();
           case 4: return new DanggiaoFragment();
           case 5: return new ThanhcongFragment();
           default:return new AllOrderFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
