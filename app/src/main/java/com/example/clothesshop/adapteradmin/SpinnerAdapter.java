package com.example.clothesshop.adapteradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clothesshop.R;
import com.example.clothesshop.model.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Categories> {
   Context context;
   ArrayList<Categories> arrayList;
    ImageView imageView;
    TextView textView;
    public SpinnerAdapter(@NonNull Context context, ArrayList<Categories> arrayList) {
        super(context, 0,arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
           if(holder==null){
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               holder = inflater.inflate(R.layout.iteam_spinner, null);
                imageView = holder.findViewById(R.id.spinner_image);
                textView = holder.findViewById(R.id.spinner_name);
           }
        Categories categories = arrayList.get(position);
           if(categories!= null){
               Picasso.get().load(categories.getImage()).into(imageView);
               textView.setText(categories.getName());
           }
        return holder;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if(holder==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = inflater.inflate(R.layout.iteam_spinner, null);
            imageView = holder.findViewById(R.id.spinner_image);
            textView = holder.findViewById(R.id.spinner_name);
        }
        Categories categories = arrayList.get(position);
        if(categories!= null){
            Picasso.get().load(categories.getImage()).into(imageView);
            textView.setText(categories.getName());
        }
        return holder;
    }
}
