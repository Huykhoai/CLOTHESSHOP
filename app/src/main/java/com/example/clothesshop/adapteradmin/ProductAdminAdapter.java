package com.example.clothesshop.adapteradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.interFace.CategoryInterfer;
import com.example.clothesshop.model.Products;
import com.example.clothesshop.server.server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.Viewholder> {
    Context context;
    ArrayList<Products> arrayList;
    CategoryInterfer interfer;

    public ProductAdminAdapter(Context context, ArrayList<Products> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public void onClickProduct(CategoryInterfer interfer){
        this.interfer = interfer;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_product_admin,parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       Products products = arrayList.get(position);
       holder.txtname_product.setText(products.getName());
       holder.txtname_product.setMaxLines(2);
       holder.txtname_product.setEllipsize(TextUtils.TruncateAt.END);
       holder.txtinventory_product.setText("Kho: "+products.getInventory());
       holder.txtprice_product.setText("Giá: $"+products.getPrice());
       String arrUrl = products.getImage();
       String[] urlImage  = arrUrl.split(",");
        Picasso.get().load(urlImage[0]).into(holder.imageView);
        holder.imgdelete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setTitle("Chú ý");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancudproduct, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                              if(response.equals("1")){
                                  Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                  arrayList.remove(products);
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
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("status", String.valueOf(2));
                                hashMap.put("id", String.valueOf(products.getId()));
                                return hashMap;
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
        holder.imgedit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interfer!=null){
                    interfer.onItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
         ImageView imageView,imgedit_product,imgdelete_product;
         TextView txtname_product,txtinventory_product,txtprice_product;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_product_admin);
            txtname_product = itemView.findViewById(R.id.name_product_admin);
            txtinventory_product = itemView.findViewById(R.id.inventory_product_admin);
            txtprice_product = itemView.findViewById(R.id.price_product_admin);
            imgedit_product = itemView.findViewById(R.id.edit_product_admin);
            imgdelete_product = itemView.findViewById(R.id.delete_product_admin);
        }
    }
}
