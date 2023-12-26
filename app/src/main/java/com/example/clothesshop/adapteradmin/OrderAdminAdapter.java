package com.example.clothesshop.adapteradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.OrderActivity;
import com.example.clothesshop.activity.OrderInformationActivity;
import com.example.clothesshop.adapter.OrderAdapter;
import com.example.clothesshop.fragment.DaHuyFragment;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;
import com.example.clothesshop.server.server;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderAdminAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayList;
    public OrderAdminAdapter(Context context, ArrayList<Order> arrayList) {
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
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_order_admin, null);
            viewHolder.imageView = view.findViewById(R.id.image_order);
            viewHolder.txtid_order = view.findViewById(R.id.txtIdorder);
            viewHolder.txtname_order = view.findViewById(R.id.txtname_order_admin);
            viewHolder.txtsize_order = view.findViewById(R.id.txtsize_order_admin);
            viewHolder.txtcolor_order = view.findViewById(R.id.txtcolor_order_admin);
            viewHolder.txtprice_order = view.findViewById(R.id.txtgia_order_admin);
            viewHolder.txtquality_order = view.findViewById(R.id.txtquality_order_admin);
            viewHolder.txtstatus_order = view.findViewById(R.id.txtchoxacnhan_order_admin);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Order order = arrayList.get(i);
        if(order.getStatus()==1){
            viewHolder.txtstatus_order.setText("Chờ Xác nhận");
        }if(order.getStatus()==3){
            viewHolder.txtstatus_order.setText("Xác nhận");
        }if(order.getStatus()==2){
            viewHolder.txtstatus_order.setText("Đã hủy");
        }if(order.getStatus()==4){
            viewHolder.txtstatus_order.setText("Đang giao");
        }if(order.getStatus()==5){
            viewHolder.txtstatus_order.setText("Thành công");
        }
        viewHolder.txtid_order.setText("ID: "+order.getIdOrder());
        viewHolder.txtname_order.setText(order.getName());
        ArrayList<Cart> arrCart= order.getList();
        Cart cart = arrCart.get(0);
        viewHolder.txtname_order.setText(cart.getProductName());
        viewHolder.txtname_order.setMaxLines(2);
        viewHolder.txtname_order.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtsize_order.setText("Size: "+cart.getSize());
        viewHolder.txtcolor_order.setText("Color: "+ cart.getColor());
        viewHolder.txtquality_order.setText("x"+ cart.getQuantity());
        viewHolder.txtprice_order.setText("$"+cart.getPrice());
        Picasso.get().load(cart.getProductImage()).into(viewHolder.imageView);
        return view;
    }
    public class ViewHolder{
        ImageView imageView;
        TextView txtid_order,txtname_order, txtsize_order,txtcolor_order, txtprice_order, txtquality_order,txtstatus_order;
    }
}
