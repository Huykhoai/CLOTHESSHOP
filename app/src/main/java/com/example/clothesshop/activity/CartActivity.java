package com.example.clothesshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static TextView txtTongtien,txtThongbao,txttongsp;
    TextView btnDathang;
    CartAdapter adapter;
    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        getData();
        CheckData();
        UpdateTongtien();
        OnclickButton();
        ActionBar();
    }

    private void OnclickButton() {
        if(MainActivity_user.userCart.size()<1){
            btnDathang.setEnabled(false);
        }else {
            btnDathang.setOnClickListener(view -> {startActivity(new Intent(CartActivity.this, CheckOutActivity.class));});
        }
    }

    private void Anhxa() {
        listView = findViewById(R.id.listViewOrder);
        txtThongbao = findViewById(R.id.txtThongbao);
        txtTongtien = findViewById(R.id.txtgiatri);
        txttongsp = findViewById(R.id.txttongsp);
        btnDathang = findViewById(R.id.btnDathang_order);
        toolbar = findViewById(R.id.toolbar_cart);
    }

    public static void UpdateTongtien() {
        long tongtien=0;
        long quality=0;
        for (int i = 0; i<MainActivity_user.userCart.size(); i++){
            tongtien+= MainActivity_user.userCart.get(i).getPrice();
            quality +=MainActivity_user.userCart.get(i).getQuantity();
        }
        txtTongtien.setText("Tổng tiền: "+ "$"+tongtien);
        txttongsp.setText("( "+quality+" Sản phẩm"+" )");
    }

    private void getData() {
        adapter = new CartAdapter(CartActivity.this, MainActivity_user.userCart);
        listView.setAdapter(adapter);
    }
    private void CheckData() {
        if(MainActivity_user.userCart.size()<1){
            txtThongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();

        }else {
            txtThongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
   private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}