package com.example.clothesshop.activity;

import androidx.appcompat.app.AppCompatActivity;
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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    ArrayList<Cart> listCart;
    InfomationAdapter adapter;
    ListView listView;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        AnhXa();
        getData();
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
           String text = "Đã hủy";
           SpannableString spannableString = new SpannableString(text);
           spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
           statusCreated.setText(spannableString);
       }
       if(order.getStatus()==3){
           statusCreated.setText("Xác nhận");
       }
       if(order.getStatus()==4){
           statusCreated.setText("Đang giao");
       }
       if(order.getStatus()==5){
           statusCreated.setText("Thành công");
       }
       listCart.addAll(order.getList());
       adapter.notifyDataSetChanged();
    }


    private void AnhXa() {
        total_item = findViewById(R.id.total_item_order_info);
        total = findViewById(R.id.total_order_info);
        delivery_fee = findViewById(R.id.delivery_fee_order_info);
        name_user_order = findViewById(R.id.name_user_order_info);
        phone_user_order = findViewById(R.id.phone_user_order_info);
        address_user_order = findViewById(R.id.address_user_order_info);
        dateCreated = findViewById(R.id.dateCreated_order_info);
        statusCreated = findViewById(R.id.statusCreated_order_info);
     listView = findViewById(R.id.listview_Order_detail);
     listCart = new ArrayList<>();
     adapter = new InfomationAdapter(OrderInformationActivity.this,listCart);
     listView.setAdapter(adapter);
    }
}