package com.example.clothesshop.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.example.clothesshop.model.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Products> arrayList;
    RecycleInterface anInterface;

    public void setOnlickProduct(RecycleInterface anInterface){
        this.anInterface = anInterface;
    }
    public ProductAdapter(Context context, ArrayList<Products> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products products =arrayList.get(position);
        holder.txtNameproduct.setText(products.getName());
        holder.txtNameproduct.setMaxLines(1);
        holder.txtNameproduct.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceproduct.setText("$"+decimalFormat.format(products.getPrice()));

        String urlImages = products.getImage();
        String[] imageUrls = urlImages.split(",");
        String image = imageUrls.length>0 ? imageUrls[0]: "";
        Picasso.get().load(image)
                .placeholder(R.drawable.baseline_home_24)
                .error(R.drawable.baseline_error_24)
                .into(holder.imageProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(anInterface!= null){
                    anInterface.onClickItemRecycleView(position);
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
        TextView txtNameproduct,txtPriceproduct;
        ImageView imageProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameproduct = itemView.findViewById(R.id.txt_name_product);
            txtPriceproduct = itemView.findViewById(R.id.txt_price_product);
            imageProduct = itemView.findViewById(R.id.image_product);
        }
    }
}
