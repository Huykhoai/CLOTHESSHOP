package com.example.clothesshop.adapteradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.clothesshop.model.Users;
import com.example.clothesshop.server.server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdminAdapter extends RecyclerView.Adapter<CustomerAdminAdapter.ViewHolder> {
    Context context;
    ArrayList<Users> arrayList;
    String a="*";
    CategoryInterfer interfer;
    public CustomerAdminAdapter(Context context, ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public void setOnclickUser(CategoryInterfer interfer){
        this.interfer = interfer;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_user_admin, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = arrayList.get(position);
        if(users.getRole()==1){
            holder.txtname.setText("Name: "+users.getName()+"(admin)");
        }else {
            holder.txtname.setText("Name: "+users.getName());
        }
        holder.txtphone.setText("Phone: +84 "+users.getPhone());
        String str = users.getPass();
        for(int i=0;i<str.length();i++){
           a = a.concat("*");
        }
        holder.txtpass.setText("Password: "+a);
        holder.txtpass.setMaxLines(1);
        holder.txtpass.setEllipsize(TextUtils.TruncateAt.END);
        a="";
        if(users.getAddress().equals("")){
            holder.txtaddress.setText("Address: (trống)");
        }else {
            holder.txtaddress.setText("Address: "+users.getAddress());
        }
        if(users.getAvatar().equals("")){
            holder.imageView.setImageResource(R.drawable.avatar);
        }else {
            Picasso.get().load(users.getAvatar()).into(holder.imageView);
        }
        
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chú ý!");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancruduser, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                  if(response.equals("1")){
                                      arrayList.remove(users);
                                      notifyDataSetChanged();
                                      Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                                  }else{
                                      Log.d("deleteuser", "onResponse: "+response);
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
                                hashMap.put("phone", String.valueOf(users.getPhone()));
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
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(interfer!= null){
                     interfer.onItem(position);
                 }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView txtname,txtphone,txtpass,txtaddress;
         ImageView imageView, imgedit,imgdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.name_user_admin);
            txtphone = itemView.findViewById(R.id.phone_user_admin);
            txtpass = itemView.findViewById(R.id.password_user_admin);
            txtaddress = itemView.findViewById(R.id.address_user_admin);
            imageView = itemView.findViewById(R.id.image_user_admin);
            imgedit = itemView.findViewById(R.id.edit_user_admin);
            imgdelete = itemView.findViewById(R.id.delete_user_admin);
        }
    }
}
