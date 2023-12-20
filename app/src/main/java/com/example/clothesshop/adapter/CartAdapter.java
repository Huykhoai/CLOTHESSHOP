package com.example.clothesshop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.MainActivity_user;
import com.example.clothesshop.activity.CartActivity;
import com.example.clothesshop.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrayList;

    public CartAdapter(Context context, ArrayList<Cart> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_cart, null);
            viewHolder.imageView = view.findViewById(R.id.image_cart);
            viewHolder.txtName = view.findViewById(R.id.name_cart);
            viewHolder.txtPrice = view.findViewById(R.id.price_cart);
            viewHolder.txtSize = view.findViewById(R.id.size_cart);
            viewHolder.txtColor = view.findViewById(R.id.color_cart);
            viewHolder.imageDelete = view.findViewById(R.id.imagedelete_cart);
            viewHolder.txtGiatri = view.findViewById(R.id.btnGiatri_cart);
            viewHolder.imageGiam = view.findViewById(R.id.btntru_cart);
            viewHolder.imageTang = view.findViewById(R.id.btncong_cart);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Cart cart = arrayList.get(i);
        viewHolder.txtName.setText(cart.getProductName());
        viewHolder.txtName.setMaxLines(2);
        viewHolder.txtName.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtPrice.setText("$"+ cart.getPrice());
        viewHolder.txtSize.setText("Size: "+ cart.getSize());
        viewHolder.txtColor.setText("Color: "+ cart.getColor());
        Picasso.get().load(cart.getProductImage()).into(viewHolder.imageView);
        viewHolder.imageDelete.setOnClickListener(view1 -> {
            if(MainActivity_user.userCart.size()==0){
                CartActivity.txtThongbao.setVisibility(View.VISIBLE);
            }else {
                CartActivity.txtThongbao.setVisibility(View.INVISIBLE);
                MainActivity_user.userCart.remove(cart);
                notifyDataSetChanged();
                CartActivity.UpdateTongtien();
                if(MainActivity_user.userCart.size()==0){
                    CartActivity.txtThongbao.setVisibility(View.VISIBLE);
                }else {
                    CartActivity.txtThongbao.setVisibility(View.INVISIBLE);
                    MainActivity_user.userCart.remove(cart);
                    notifyDataSetChanged();
                    CartActivity.UpdateTongtien();
                }
            }
        });
        viewHolder.txtGiatri.setText(String.valueOf(cart.getQuantity()));

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.imageGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.txtGiatri.getText().toString())-1;
                int slhientai = MainActivity_user.userCart.get(i).getQuantity();
                long giahientai = MainActivity_user.userCart.get(i).getPrice();
                MainActivity_user.userCart.get(i).setQuantity(slmoinhat);
                //gia moi
                long giamoinhat = (slmoinhat* giahientai)/slhientai;
                MainActivity_user.userCart.get(i).setPrice((int) giamoinhat);
                CartActivity.UpdateTongtien();
                finalViewHolder.txtPrice.setText("$"+giamoinhat);
                if(slmoinhat==1){
                    finalViewHolder.imageTang.setEnabled(true);
                    finalViewHolder.txtGiatri.setText(String.valueOf(slmoinhat));
                    finalViewHolder.imageGiam.setEnabled(false);
                }else {
                    finalViewHolder.imageTang.setEnabled(true);
                    finalViewHolder.txtGiatri.setText(String.valueOf(slmoinhat));
                    finalViewHolder.imageGiam.setEnabled(true);
                }
            }
        });
        viewHolder.imageTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.txtGiatri.getText().toString())+1;
                int slhientai = MainActivity_user.userCart.get(i).getQuantity();
                long giahientai = MainActivity_user.userCart.get(i).getPrice();
                MainActivity_user.userCart.get(i).setQuantity(slmoinhat);
                //gia moi
                long giamoinhat = (slmoinhat* giahientai)/slhientai;
                MainActivity_user.userCart.get(i).setPrice((int) giamoinhat);
                CartActivity.UpdateTongtien();
                finalViewHolder.txtPrice.setText("$"+giamoinhat);
                if(slmoinhat>9){
                    finalViewHolder.imageTang.setEnabled(false);
                    finalViewHolder.txtGiatri.setText(String.valueOf(slmoinhat));
                    finalViewHolder.imageGiam.setEnabled(true);
                }else {
                    finalViewHolder.imageTang.setEnabled(true);
                    finalViewHolder.txtGiatri.setText(String.valueOf(slmoinhat));
                    finalViewHolder.imageGiam.setEnabled(true);
                }
            }
        });
        return view;
    }
    public class ViewHolder{
        ImageView imageView,imageDelete,imageGiam, imageTang;
        TextView txtName,txtPrice,txtSize,txtColor,txtGiatri;

    }
}
