package com.example.clothesshop.adapter;

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

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayList;
    public OrderAdapter(Context context, ArrayList<Order> arrayList) {
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
            view = inflater.inflate(R.layout.item_order, null);
            viewHolder.imageView = view.findViewById(R.id.image_order);
            viewHolder.txtname_order = view.findViewById(R.id.txtname_order);
            viewHolder.txtsize_order = view.findViewById(R.id.txtsize_order);
            viewHolder.txtcolor_order = view.findViewById(R.id.txtcolor_order);
            viewHolder.txtprice_order = view.findViewById(R.id.txtgia_order);
            viewHolder.txtquality_order = view.findViewById(R.id.txtquality_order);
            viewHolder.txtAllquality_order = view.findViewById(R.id.txtqualityall_order);
            viewHolder.txttotal_order = view.findViewById(R.id.txttongtien_order);
            viewHolder.txtstatus_order = view.findViewById(R.id.txtchoxacnhan_order);
            viewHolder.btnCancel_order = view.findViewById(R.id.btnCancel_order);
            viewHolder.txtdetail_order = view.findViewById(R.id.txtdetail_order);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Order order = arrayList.get(i);
        if(order.getStatus()==0){
            viewHolder.txtstatus_order.setText("Chờ Xác nhận");
        }if(order.getStatus()==1){
            viewHolder.txtstatus_order.setText("Xác nhận");
        }if(order.getStatus()==2){
            viewHolder.txtstatus_order.setText("Đã hủy");
            viewHolder.btnCancel_order.setText("Đã hủy");
            viewHolder.btnCancel_order.setEnabled(false);
        }if(order.getStatus()==3){
            viewHolder.txtstatus_order.setText("Đang giao");
        }if(order.getStatus()==4){
            viewHolder.txtstatus_order.setText("Thành công");
        }
        viewHolder.txtname_order.setText(order.getName());
        viewHolder.txttotal_order.setText(""+order.getTotal());

        ArrayList<Cart> arrCart= order.getList();
        Log.d("cartArr", "getView: "+ arrCart.toString());
        Cart cart = arrCart.get(0);
        viewHolder.txtname_order.setText(cart.getProductName());
        viewHolder.txtname_order.setMaxLines(2);
        viewHolder.txtname_order.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtsize_order.setText("Size: "+cart.getSize());
        viewHolder.txtcolor_order.setText("Color: "+ cart.getColor());
        viewHolder.txtquality_order.setText(""+ cart.getQuantity());
        viewHolder.txtprice_order.setText("$"+cart.getPrice());
        Picasso.get().load(cart.getProductImage()).into(viewHolder.imageView);

        viewHolder.btnCancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Lưu ý!");
                builder.setMessage("Xác nhận hủy!");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateCancel = spf.format(new Date());
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdatestatus, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                 if(response.equals("1")){
                                     notifyDataSetChanged();
                                 }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> params = new HashMap<>();
                                params.put("status", String.valueOf(2));
                                params.put("idorder", order.getIdOrder());
                                params.put("datecancel",dateCancel);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        viewHolder.txtdetail_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_detail", order);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }
    public class ViewHolder{
        ImageView imageView;
        TextView txtname_order, txtsize_order,txtcolor_order, txtprice_order, txtquality_order,txtAllquality_order, txttotal_order,txtstatus_order,txtdetail_order;
        TextView btnCancel_order;
    }
}
