package com.example.clothesshop.adapteradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.clothesshop.model.Categories;
import com.example.clothesshop.server.server;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryAdminAdapter extends RecyclerView.Adapter<CategoryAdminAdapter.ViewHolder>{
    Context context;
    ArrayList<Categories> arrayList;
    CategoryInterfer interfer;
    public CategoryAdminAdapter(Context context, ArrayList<Categories> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
   public void setOnclickCategory(CategoryInterfer interfer){
        this.interfer = interfer;
   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           Categories categories = arrayList.get(position);
           holder.txtname.setText(categories.getName());
           Picasso.get().load(categories.getImage()).into(holder.image_category);
           holder.imagedelete.setOnClickListener(view -> {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Chú ý!");
               builder.setMessage("Bạn có muốn xóa không?");
               builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       RequestQueue requestQueue = Volley.newRequestQueue(context);
                       StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdaninsertcategory, new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               if(response.equals("1")){
                                   Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                   arrayList.remove(categories);
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
                               params.put("id", String.valueOf(categories.getId()));
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
           });
           holder.image_edit.setOnClickListener(view -> {
               if(interfer!=null){
                   interfer.onItem(position);
               }
           });
    }

    @Override
    public int getItemCount() {
        if(arrayList.size()==0)
            return 0;
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
          ImageView image_category, imagedelete,image_edit;
          TextView txtname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_category = itemView.findViewById(R.id.image_category_admin);
            imagedelete = itemView.findViewById(R.id.delete_category_admin);
            image_edit = itemView.findViewById(R.id.edit_category_admin);
            txtname = itemView.findViewById(R.id.name_category_admin);
        }
    }
}
