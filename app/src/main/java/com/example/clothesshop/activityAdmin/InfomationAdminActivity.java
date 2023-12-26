package com.example.clothesshop.activityAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.OrderInformationActivity;
import com.example.clothesshop.adapter.InfomationAdapter;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;
import com.example.clothesshop.server.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfomationAdminActivity extends AppCompatActivity {
    TextView dateCreated, dateConfirm, dateCanceled, dateDelivery, dateSuccess, statusCreated, statusConfirm, statusCancel, statusDelivery, statusSuccess,
            name_user_order, phone_user_order, address_user_order, total_item, delivery_fee, total;
    ArrayList<Cart> listCart;
    InfomationAdapter adapter;
    ListView listView;
    Order order;
    Toolbar toolbar;
    Spinner spinner;
    Button btnSubmit;
    int statusInt;
    SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_admin);
        AnhXa();
        getData();
        spinerStatus();
        Actionbar();
        BtnSubmit();
    }

    private void BtnSubmit() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(InfomationAdminActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdatestatus, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int updatedStatus = jsonResponse.getInt("status");
                            String updatedDate = jsonResponse.getString("date");
                            switch (updatedStatus) {
                                case 2:
                                    statusCancel.setText("Đã hủy");
                                    dateCanceled.setText(updatedDate);
                                    statusCancel.setVisibility(View.VISIBLE);
                                    dateCanceled.setVisibility(View.VISIBLE);
                                    break;
                                case 3:
                                    statusConfirm.setText("Xác nhận");
                                    dateConfirm.setText(updatedDate);
                                    statusConfirm.setVisibility(View.VISIBLE);
                                    dateConfirm.setVisibility(View.VISIBLE);
                                    break;
                                case 4:
                                    statusDelivery.setText("Đang giao");
                                    dateDelivery.setText(updatedDate);
                                    statusDelivery.setVisibility(View.VISIBLE);
                                    dateDelivery.setVisibility(View.VISIBLE);
                                    break;
                                case 5:
                                    statusSuccess.setText("Thành công");
                                    dateSuccess.setText(updatedDate);
                                    statusSuccess.setVisibility(View.VISIBLE);
                                    dateSuccess.setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("idorder",order.getIdOrder());
                        hashMap.put("status", String.valueOf(statusInt));
                        String datetime = spd.format(new Date());
                        switch (statusInt){
                            case 2:
                                hashMap.put("datecancel",datetime);
                                break;
                            case 3:
                                hashMap.put("dateconfirm", datetime);
                                break;
                            case 4:
                                hashMap.put("datedelivery", datetime);
                                break;
                            case 5:
                                hashMap.put("datesuccess", datetime);
                                break;
                            default:
                                break;
                        }
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void spinerStatus() {
        List<String> status = new ArrayList<>();
        status.add("Xác Nhận");
        status.add("Hủy");
        status.add("Đang giao");
        status.add("Thành công");
        if(order.getStatus()==1){
            status.remove("Đang giao");
            status.remove("Thành công");
        }
        if (order.getStatus() == 3) {
           status.remove("Xác nhận");
        } else if (order.getStatus() == 2) {
            status.remove("Xác Nhận");
            status.remove("Đang giao");
            status.remove("Thành công");
        } else if (order.getStatus() == 4) {
            status.remove("Xác Nhận");
            status.remove("Hủy");
            status.remove("Đang giao");
        } else if (order.getStatus() == 5) {
            status.remove("Hủy");
            status.remove("Xác Nhận");
            status.remove("Đang giao");
            status.remove("Thành công");
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, status);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectStatus = (String) adapterView.getItemAtPosition(i);
                switch (selectStatus){
                    case "Xác nhận":
                        statusInt =3;
                        break;
                    case "Hủy":
                        statusInt = 2;
                        break;
                    case "Đang giao":
                        statusInt = 4;
                        break;
                    case "Thành công":
                        statusInt =5;
                        break;
                    default:
                        statusInt=3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        if(!order.getCreateCancel().equals("null")){
            statusCancel.setText("Đã hủy");
            dateCanceled.setText(order.getCreateCancel());
            statusCancel.setVisibility(View.VISIBLE);
            dateCanceled.setVisibility(View.VISIBLE);
            btnSubmit.setEnabled(false);
            spinner.setEnabled(false);
        }
        if(!order.getDateConfrim().equals("null")){
            statusConfirm.setText("Xác nhận");
            dateConfirm.setText(order.getDateConfrim());
            statusConfirm.setVisibility(View.VISIBLE);
            dateConfirm.setVisibility(View.VISIBLE);
        }
        if(!order.getDateDelivery().equals("null")){
            statusDelivery.setText("Đang giao");
            dateDelivery.setText(order.getDateDelivery());
            statusDelivery.setVisibility(View.VISIBLE);
            dateDelivery.setVisibility(View.VISIBLE);
        }
        if(!order.getDateSuccess().equals("null")){
            statusSuccess.setText("Thành công");
            dateSuccess.setText(order.getDateDelivery());
            statusSuccess.setVisibility(View.VISIBLE);
            dateSuccess.setVisibility(View.VISIBLE);
        }
        listCart.addAll(order.getList());
        adapter.notifyDataSetChanged();
    }
    private void AnhXa() {
        btnSubmit = findViewById(R.id.btnConfirmOrder_admin);
        spinner = findViewById(R.id.spinnerStatus_admin);
        toolbar = findViewById(R.id.toolbar_admin);
        total_item = findViewById(R.id.total_item_admin);
        total = findViewById(R.id.total_admin);
        delivery_fee = findViewById(R.id.delivery_fee_admin);
        name_user_order = findViewById(R.id.name_user_order_admin);
        phone_user_order = findViewById(R.id.phone_user_order_admin);
        address_user_order = findViewById(R.id.address_user_order_admin);
        dateCreated = findViewById(R.id.dateCreated_admin);
        statusCreated = findViewById(R.id.statusCreated_admin);
        dateCanceled = findViewById(R.id.dateCanceled_admin);
        statusCancel = findViewById(R.id.statusCancel_admin);
        dateConfirm = findViewById(R.id.dateConfirm_admin);
        statusConfirm = findViewById(R.id.statusConfirm_admin);
        dateDelivery = findViewById(R.id.dateDelivery_admin);
        statusDelivery = findViewById(R.id.statusDelivery_admin);
        dateSuccess = findViewById(R.id.dateSuccess_admin);
        statusSuccess = findViewById(R.id.statusSuccess_admin);
        listView = findViewById(R.id.listview_Order_detail_admin);
        listCart = new ArrayList<>();
        adapter = new InfomationAdapter(InfomationAdminActivity.this,listCart);
        listView.setAdapter(adapter);
    }
}