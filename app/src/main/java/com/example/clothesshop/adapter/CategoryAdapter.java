package com.example.clothesshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothesshop.R;
import com.example.clothesshop.interFace.RecycleInterface;
import com.example.clothesshop.model.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
   Context context;
   ArrayList<Categories> arrayList;
   RecycleInterface recycleInterface;
    public void OnClickItem(RecycleInterface interfaceRe){
        this.recycleInterface = interfaceRe;
    }
    public CategoryAdapter(Context context, ArrayList<Categories> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categories categories = arrayList.get(position);
        holder.txtNameCategory.setText(categories.getName());
        Picasso.get().load(categories.getImage())
                .placeholder(R.drawable.baseline_home_24)
                .error(R.drawable.baseline_error_24)
                .into(holder.imageCategory);
        holder.imageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recycleInterface!= null){
                    recycleInterface.onClickItemRecycleView(position);
                }else {
                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameCategory;
        ImageView imageCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCategory = itemView.findViewById(R.id.name_category);
            imageCategory = itemView.findViewById(R.id.image_category);
        }
    }
}
