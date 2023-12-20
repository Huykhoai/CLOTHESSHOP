package com.example.clothesshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.clothesshop.R;
import com.example.clothesshop.model.Photo;


public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_photo, container, false);
        Bundle bundle =getArguments();
        Photo photo = (Photo) bundle.getSerializable("objImage");
        ImageView imageView = view.findViewById(R.id.photo_fragment);
        imageView.setImageResource(photo.getImage());
        return view;
    }
}