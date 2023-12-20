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
import com.example.clothesshop.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfomationAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrayList;

    public InfomationAdapter(Context context, ArrayList<Cart> arrayList) {
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
            view = inflater.inflate(R.layout.item_infomation, null);
            viewHolder.imageView = view.findViewById(R.id.image_info);
            viewHolder.txtName = view.findViewById(R.id.name_info);
            viewHolder.txtPrice = view.findViewById(R.id.price_info);
            viewHolder.txtSize = view.findViewById(R.id.size_info);
            viewHolder.txtColor = view.findViewById(R.id.color_info);
            viewHolder.txtquality = view.findViewById(R.id.quality_info);
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
        viewHolder.txtquality.setText("x"+cart.getQuantity());
        Picasso.get().load(cart.getProductImage()).into(viewHolder.imageView);
        return view;
    }
    public class ViewHolder{
        TextView txtName,txtPrice,txtSize,txtColor,txtquality;
        ImageView imageView ;
    }
}
