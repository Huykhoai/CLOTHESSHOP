package com.example.clothesshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.Detail_viewpaper;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Products;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class DetailProductActivity extends AppCompatActivity {
      TextView txtName_detail,txtPrice_detail,txtDesc_detai;
      TextView btnM,btnL,btnXl,btnBlack,btnRed,btnWhite,btnGray;

      ViewPager viewPager;
      String urlImages,size="",color="";
      Detail_viewpaper adapterViewpaper;
      ImageView btnBack,btncancel;
      CircleIndicator circleIndicator;
      LinearLayout btnChat,btnThemgiohang,btnDathang;
      Products products;
      int id=0;
      String name="";
      int price=0;
      int sl=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        AnhXa();
        getData();
        ViewPaperDetail();
        onClickButton();

    }

    private void onClickButton() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProductActivity.this, "chat", Toast.LENGTH_SHORT).show();
            }
        });
        btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  onClickAddCart();
            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickBuyNow();
            }
        });

    }

    private void ViewPaperDetail() {
        String[] imageSplit = urlImages.split(",");
        adapterViewpaper = new Detail_viewpaper(DetailProductActivity.this,imageSplit);
        viewPager.setAdapter(adapterViewpaper);
        circleIndicator.setViewPager(viewPager);

    }

    private void AnhXa() {
        txtName_detail = findViewById(R.id.name_detail_product);
        txtPrice_detail = findViewById(R.id.gia_detail_product);
        txtDesc_detai = findViewById(R.id.txtdesc_detail_product);
        viewPager = findViewById(R.id.viewPaper_detail_product);
        btnBack = findViewById(R.id.back_button);
        circleIndicator = findViewById(R.id.circleIndicator_detail);
        btnChat = findViewById(R.id.btnChatngay);
        btnThemgiohang = findViewById(R.id.btnThemgiohang);
        btnDathang = findViewById(R.id.btnDathang_detail);
    }
   private void getData(){
       Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
        products = (Products) bundle.getSerializable("detailproduct");
        id = products.getId();
        name = products.getName();
        price = products.getPrice();
        urlImages = products.getImage();

       txtName_detail.setText(products.getName());
       txtPrice_detail.setText("$"+products.getPrice());
       txtDesc_detai.setText(products.getDescription());
   }
   private void onClickSize(){
       btnM.setOnClickListener(view -> {
           clearChooseBtnSize();
        size = btnM.getText().toString();
        btnM.setBackgroundResource(R.drawable.bg_button_size_color_selected);
        btnM.setTextColor(getResources().getColor(R.color.white));
       });
       btnL.setOnClickListener(view -> {
           clearChooseBtnSize();
           size = btnL.getText().toString();
           btnL.setBackgroundResource(R.drawable.bg_button_size_color_selected);
           btnL.setTextColor(getResources().getColor(R.color.white));
       });
       btnXl.setOnClickListener(view -> {
           clearChooseBtnSize();
           size = btnXl.getText().toString();
           btnXl.setBackgroundResource(R.drawable.bg_button_size_color_selected);
           btnXl.setTextColor(getResources().getColor(R.color.white));
       });
   }
   private void onClickColor(){
        btnBlack.setOnClickListener(view -> {
                clearChooseColor();
                color = btnBlack.getText().toString();

                btnBlack.setBackgroundResource(R.drawable.bg_button_size_color_selected);
                btnBlack.setTextColor(getResources().getColor(R.color.white));

        });
       btnWhite.setOnClickListener(view -> {
               clearChooseColor();
               color = btnWhite.getText().toString();
               btnWhite.setBackgroundResource(R.drawable.bg_button_size_color_selected);
               btnWhite.setTextColor(getResources().getColor(R.color.white));

       });
       btnGray.setOnClickListener(view -> {
               clearChooseColor();
               color = btnGray.getText().toString();
               btnGray.setBackgroundResource(R.drawable.bg_button_size_color_selected);
               btnGray.setTextColor(getResources().getColor(R.color.white));

       });
       btnRed.setOnClickListener(view -> {
               clearChooseColor();
               color = btnRed.getText().toString();
               btnRed.setBackgroundResource(R.drawable.bg_button_size_color_selected);
               btnRed.setTextColor(getResources().getColor(R.color.white));
       });
   }
    private void clearChooseBtnSize() {
        size = "";
        btnL.setBackgroundResource(R.drawable.bg_button_size_color);
        btnM.setBackgroundResource(R.drawable.bg_button_size_color);
        btnXl.setBackgroundResource(R.drawable.bg_button_size_color);
        btnL.setTextColor(getResources().getColor(R.color.black));
        btnM.setTextColor(getResources().getColor(R.color.black));
        btnXl.setTextColor(getResources().getColor(R.color.black));

    }
    private void clearChooseColor() {
        color = "";
        btnWhite.setBackgroundResource(R.drawable.bg_button_size_color);
        btnRed.setBackgroundResource(R.drawable.bg_button_size_color);
        btnGray.setBackgroundResource(R.drawable.bg_button_size_color);
        btnBlack.setBackgroundResource(R.drawable.bg_button_size_color);

        btnWhite.setTextColor(getResources().getColor(R.color.black));
        btnRed.setTextColor(getResources().getColor(R.color.black));
        btnGray.setTextColor(getResources().getColor(R.color.black));
        btnBlack.setTextColor(getResources().getColor(R.color.black));
    }
    private int validate() {
        if (size.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
            return -1;
        }

        if (color.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn màu sắc", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }
    private void onclickBuyNow(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DetailProductActivity.this);
        View bottomsetView = getLayoutInflater().inflate(R.layout.item_dathang, null);
        bottomSheetDialog.setContentView(bottomsetView);
        //
        ViewGroup.LayoutParams params = bottomsetView.getLayoutParams();
        params.height = (int) (getResources().getDisplayMetrics().heightPixels *0.57);
        bottomsetView.setLayoutParams(params);
        //
        ImageView imageView = bottomSheetDialog.findViewById(R.id.image_dathang);
        TextView txt_gia_dathang = bottomSheetDialog.findViewById(R.id.txt_gia_dathang);
        TextView txt_kho_dathang = bottomSheetDialog.findViewById(R.id.txt_kho_dathang);
        Button btnDathang = bottomSheetDialog.findViewById(R.id.btnDathang);
        ImageView imageCong =bottomSheetDialog.findViewById(R.id.btncong_dathang);
        ImageView imageTru =bottomSheetDialog.findViewById(R.id.btntru_donhang);
        TextView txtGiatri = bottomSheetDialog.findViewById(R.id.btnGiatri);
        btnM = bottomSheetDialog.findViewById(R.id.btnM);
        btnL = bottomSheetDialog.findViewById(R.id.btnL);
        btnXl = bottomSheetDialog.findViewById(R.id.btnXL);
        btnWhite = bottomSheetDialog.findViewById(R.id.btnWhite);
        btnBlack = bottomSheetDialog.findViewById(R.id.btnBlack);
        btnGray = bottomSheetDialog.findViewById(R.id.btnGray);
        btnRed = bottomSheetDialog.findViewById(R.id.btnRed);
        btncancel=bottomSheetDialog.findViewById(R.id.dialog_cancel);
        onClickSize();
        onClickColor();
        //
        txt_gia_dathang.setText("Giá: "+"$"+products.getPrice());
        txt_kho_dathang.setText("Kho: "+products.getInventory());

        String[] urlImage_dathang = urlImages.split(",");
        Picasso.get().load(urlImage_dathang[0])
                .placeholder(R.drawable.baseline_home_24)
                .error(R.drawable.baseline_error_24)
                .into(imageView);
        //
        int sl = Integer.parseInt(txtGiatri.getText().toString());
        if(sl>9){
            imageCong.setEnabled(false);
            imageTru.setEnabled(true);
        }else if(sl==1){
            imageTru.setEnabled(false);
            imageCong.setEnabled(true);
        }
        imageCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(txtGiatri.getText().toString())+1;
                if(slmoinhat>9){
                    imageCong.setEnabled(false);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }else {
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }
            }
        });
        imageTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(txtGiatri.getText().toString())-1;
                if(slmoinhat==1){
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(false);
                }else {
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }
            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()>0){
                    Intent intent = new Intent(DetailProductActivity.this,CheckOutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("detail_product", products);
                    bundle.putString("color", color);
                    bundle.putString("size" , size);
                    bundle.putString("quality", txtGiatri.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    bottomSheetDialog.dismiss();
                }
            }
        });
        btncancel.setOnClickListener(view1 -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }
    private void onClickAddCart(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DetailProductActivity.this);
        View bottomsetView = getLayoutInflater().inflate(R.layout.item_dathang, null);
        bottomSheetDialog.setContentView(bottomsetView);
        //
        ViewGroup.LayoutParams params = bottomsetView.getLayoutParams();
        params.height = (int) (getResources().getDisplayMetrics().heightPixels *0.57);
        bottomsetView.setLayoutParams(params);
        //
        ImageView imageView = bottomSheetDialog.findViewById(R.id.image_dathang);
        TextView txt_gia_dathang = bottomSheetDialog.findViewById(R.id.txt_gia_dathang);
        TextView txt_kho_dathang = bottomSheetDialog.findViewById(R.id.txt_kho_dathang);
        Button btnDathang = bottomSheetDialog.findViewById(R.id.btnDathang);
        ImageView imageCong =bottomSheetDialog.findViewById(R.id.btncong_dathang);
        ImageView imageTru =bottomSheetDialog.findViewById(R.id.btntru_donhang);
        TextView txtGiatri = bottomSheetDialog.findViewById(R.id.btnGiatri);
        btnM = bottomSheetDialog.findViewById(R.id.btnM);
        btnL = bottomSheetDialog.findViewById(R.id.btnL);
        btnXl = bottomSheetDialog.findViewById(R.id.btnXL);
        btnWhite = bottomSheetDialog.findViewById(R.id.btnWhite);
        btnBlack = bottomSheetDialog.findViewById(R.id.btnBlack);
        btnGray = bottomSheetDialog.findViewById(R.id.btnGray);
        btnRed = bottomSheetDialog.findViewById(R.id.btnRed);
        btncancel=bottomSheetDialog.findViewById(R.id.dialog_cancel);
        onClickSize();
        onClickColor();
        //
        txt_gia_dathang.setText("Giá: "+"$"+products.getPrice());
        txt_kho_dathang.setText("Kho: "+products.getInventory());

        String[] urlImage_dathang = urlImages.split(",");
        Picasso.get().load(urlImage_dathang[0])
                .placeholder(R.drawable.baseline_home_24)
                .error(R.drawable.baseline_error_24)
                .into(imageView);
        //
        sl = Integer.parseInt(txtGiatri.getText().toString());
        if(sl>9){
            imageCong.setEnabled(false);
            imageTru.setEnabled(true);
        }else if(sl==1){
            imageTru.setEnabled(false);
            imageCong.setEnabled(true);
        }
        imageCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(txtGiatri.getText().toString())+1;
                if(slmoinhat>9){
                    imageCong.setEnabled(false);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }else {
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }
            }
        });
        imageTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(txtGiatri.getText().toString())-1;
                if(slmoinhat==1){
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(false);
                }else {
                    imageCong.setEnabled(true);
                    txtGiatri.setText(String.valueOf(slmoinhat));
                    imageTru.setEnabled(true);
                }
            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()>0){
                    if (MainActivity_user.userCart.size() > 0) {
                        int quality = Integer.parseInt(txtGiatri.getText().toString());
                        String productimage = urlImage_dathang[0];
                        boolean exits = false;

                        for (int i = 0; i < MainActivity_user.userCart.size(); i++) {
                            if (MainActivity_user.userCart.get(i).getProductId() == id
                                    && MainActivity_user.userCart.get(i).getColor().equals(color)
                                    && MainActivity_user.userCart.get(i).getSize().equals(size)) {
                                MainActivity_user.userCart.get(i).setQuantity(MainActivity_user.userCart.get(i).getQuantity() + quality);
                                MainActivity_user.userCart.get(i).setPrice(MainActivity_user.userCart.get(i).getQuantity() * price);
                                exits = true;
                            }
                        }
                        if (exits == false) {
                            int soluong = Integer.parseInt(txtGiatri.getText().toString());
                            int giamoi = price * soluong;

                            MainActivity_user.userCart.add(new Cart(id, productimage, name, size, color, soluong, giamoi));
                        }

                    } else {
                        String productimage1 = urlImage_dathang[0];
                        int soluong = Integer.parseInt(txtGiatri.getText().toString());
                        int giamoi = price * soluong;
                        MainActivity_user.userCart.add(new Cart(id, productimage1, name, size, color, soluong, giamoi));
                    }
                    bottomSheetDialog.dismiss();
                    startActivity(new Intent(DetailProductActivity.this, CartActivity.class));
                }
            }
        });
        btncancel.setOnClickListener(view1 -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }
}
