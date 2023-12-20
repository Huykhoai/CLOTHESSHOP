package com.example.clothesshop.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clothesshop.fragment.PhotoFragment;
import com.example.clothesshop.model.Photo;

import java.util.List;

public class PhotoAdapter extends FragmentStateAdapter {
    List<Photo> list;
    public PhotoAdapter(@NonNull FragmentActivity fragmentActivity, List<Photo> list) {
        super(fragmentActivity);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      Photo photo = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objImage", photo);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
