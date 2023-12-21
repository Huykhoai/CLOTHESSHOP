package com.example.clothesshop.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothesshop.R;
import com.example.clothesshop.adapter.InfomationAdapter;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderInformationActivity extends AppCompatActivity {
    TextView dateCreated, dateConfirm, dateCanceled, dateDelivery, dateSuccess, statusCreated, statusConfirm, statusCancel, statusDelivery, statusSuccess,
            name_user_order, phone_user_order, address_user_order, total_item, delivery_fee, total;
    ArrayList<Cart> listCart;
    InfomationAdapter adapter;
    ListView listView;
    Order order;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        AnhXa();
        getData();
        Actionbar();
    }
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(order.getIdOrder());
        }
    }
    private void getData() {
       Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       order = (Order) bundle.getSerializable("order_detail");
       name_user_order.setText(order.getName());
       phone_user_order.setText(""+order.getPhone());
       address_user_order.setText(order.getAddress());
       total_item.setText("$"+ order.getTotal());
       int delivity = (int) (order.getTotal() * 0.1);
       delivery_fee.setText("$"+ delivity);
       int totalInt = order.getTotal() - delivity;
       total.setText("$"+totalInt);
       dateCreated.setText(order.getCreateAt());
       if(order.getStatus()==1){
           statusCreated.setText("Chờ xác nhận");
       }
       if(order.getStatus()==2){
           statusCancel.setText("Đã hủy");
           dateCanceled.setText(order.getCreateCancel());
           statusCancel.setVisibility(View.VISIBLE);
           dateCanceled.setVisibility(View.VISIBLE);
       }
       listCart.addAll(order.getList());
       adapter.notifyDataSetChanged();
    }


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar_order_info);
        total_item = findViewById(R.id.total_item_order_info);
        total = findViewById(R.id.total_order_info);
        delivery_fee = findViewById(R.id.delivery_fee_order_info);
        name_user_order = findViewById(R.id.name_user_order_info);
        phone_user_order = findViewById(R.id.phone_user_order_info);
        address_user_order = findViewById(R.id.address_user_order_info);
        dateCreated = findViewById(R.id.dateCreated_order_info);
        statusCreated = findViewById(R.id.statusCreated_order_info);
        dateCanceled = findViewById(R.id.dateCanceled_order_info);
        statusCancel = findViewById(R.id.statusCancel_order_info);
        dateConfirm = findViewById(R.id.dateConfirm_order_info);
        statusConfirm = findViewById(R.id.statusConfirm_order_info);
        dateSuccess = findViewById(R.id.dateSuccess_order_info);
        statusSuccess = findViewById(R.id.statusSuccess_order_info);
     listView = findViewById(R.id.listview_Order_detail);
     listCart = new ArrayList<>();
     adapter = new InfomationAdapter(OrderInformationActivity.this,listCart);
     listView.setAdapter(adapter);
    }

}