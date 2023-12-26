package com.example.clothesshop.adapteradmin;

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
import com.example.clothesshop.fragmentAdmin.fragmentOrder.AllorderFragment;
import com.example.clothesshop.fragmentAdmin.fragmentOrder.ChoxacnhanOrderFragment;
import com.example.clothesshop.fragmentAdmin.fragmentOrder.DahuyorderFragment;
import com.example.clothesshop.fragmentAdmin.fragmentOrder.DanggiaoOrderFragment;
import com.example.clothesshop.fragmentAdmin.fragmentOrder.ThanhcongOrderFragment;
import com.example.clothesshop.fragmentAdmin.fragmentOrder.XacnhanOrderFragment;
import com.example.clothesshop.model.Order;

public class ViewpaperAdminAdapter extends FragmentStateAdapter {
    public ViewpaperAdminAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new AllorderFragment();
            case 1: return new ChoxacnhanOrderFragment();
            case 2: return new XacnhanOrderFragment();
            case 3: return new DahuyorderFragment();
            case 4: return new DanggiaoOrderFragment();
            case 5: return new ThanhcongOrderFragment();
            default:return new AllorderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
