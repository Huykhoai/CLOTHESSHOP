package com.example.clothesshop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.clothesshop.adapter.CartAdapter;
import com.example.clothesshop.adapter.InfomationAdapter;
import com.example.clothesshop.fragment.LoginFragment;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;
import com.example.clothesshop.model.Products;
import com.example.clothesshop.server.server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CheckOutActivity extends AppCompatActivity {
    ListView listView;
    InfomationAdapter adapter;
    ArrayList<Cart> arrayList;
    ArrayList<Order> listOrder;
    ImageView image_out_checkout;
    Products products;
    private int id, productId,quantity,price, phone_user,total;
    private String productImage,productName,size,color,name_user, address_user;
    private
    TextView txttotalitem, txtDelyvery,txttotal, txtgiatri_check,txttotalquality,
            txtname_check,txtphone_check,txtaddress_check, btnchangeaddress,btndathang_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Anhxa();
        getData();
        getProfile();
        ChangeAddress();
        ButtonDathang();
    }

    private void ButtonDathang() {

    }

    private void ChangeAddress() {
        btnchangeaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(CheckOutActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_changeaddress);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                EditText edchange = dialog.findViewById(R.id.edchangeaddress);
                TextView btnchange = dialog.findViewById(R.id.btnchange);
                btnchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String change = edchange.getText().toString();
                        if(!change.isEmpty()){
                            RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdateusers, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")){
                                        Toast.makeText(CheckOutActivity.this, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                                        txtaddress_check.setText(change);
                                        dialog.dismiss();
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
                                    HashMap<String, String> params = new HashMap<>();
                                    String name = "";
                                    String pass = "";
                                    params.put("id", String.valueOf(id));
                                    params.put("name", name);
                                    params.put("pass", pass);
                                    params.put("address", change);

                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }
                });
                dialog.show();
            }
        });
    }


    private void getProfile() {
        for(int i=0;i<LoginFragment.arrayList.size();i++){
            id = LoginFragment.arrayList.get(i).getId();
            name_user = LoginFragment.arrayList.get(i).getName();
            phone_user = LoginFragment.arrayList.get(i).getPhone();
            address_user = LoginFragment.arrayList.get(i).getAddress();

        }
        txtname_check.setText(""+ name_user);
        txtphone_check.setText(""+phone_user);
        txtaddress_check.setText(""+address_user);
    }

    private void getData() {
        Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle!= null){
            products = (Products) bundle.getSerializable("detail_product");
            size = bundle.getString("size");
            color = bundle.getString("color");
            quantity = Integer.parseInt(bundle.getString("quality"));
            productId = products.getId();
            productName = products.getName();
            productImage = products.getImage();
            price = products.getPrice();
            String[] arrUrls = productImage.split(",");
            arrayList.add(new Cart(productId,arrUrls[0], productName, size, color, quantity, price));
            adapter = new InfomationAdapter(CheckOutActivity.this,arrayList);
            listView.setAdapter(adapter);

            int totalItem = price * quantity;
            txttotalitem.setText(""+totalItem);
            int delivery = (int) (price * 0.1);
            txtDelyvery.setText(""+delivery);
            total = totalItem - delivery;
            txttotal.setText(""+ total);
            txtgiatri_check.setText(""+total);
            txttotalquality.setText("( "+quantity+" Sản phẩm"+ " )");

            image_out_checkout.setOnClickListener(view -> onBackPressed());
                btndathang_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtaddress_check.getText().toString().trim().isEmpty()){
                            Toast.makeText(CheckOutActivity.this, "Bạn phải nhập địa chỉ", Toast.LENGTH_SHORT).show();
                        }else {
                            RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanorders, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")){
                                        Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CheckOutActivity.this, OrderActivity.class));
                                    }else {
                                        Toast.makeText(CheckOutActivity.this, response, Toast.LENGTH_SHORT).show();
                                        Log.d("error_check", "onResponse: "+response);
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
                                    HashMap<String, String> params = new HashMap<>();
                                    JSONArray jsonArray = new JSONArray();
                                    Random random = new Random();
                                    int randomNumber = random.nextInt(90000) + 10000;
                                    for(int i=0;i<arrayList.size();i++){
                                        JSONObject cartJsonObject = new JSONObject();
                                        try {
                                            Cart cart = arrayList.get(i);
                                            cartJsonObject.put("cartid",cart.getProductId());
                                            cartJsonObject.put("cartname",cart.getProductName() );
                                            cartJsonObject.put("cartimage", cart.getProductImage());
                                            cartJsonObject.put("cartsize", cart.getSize());
                                            cartJsonObject.put("cartcolor", cart.getColor());
                                            cartJsonObject.put("cartquality",cart.getQuantity());
                                            cartJsonObject.put("cartprice",cart.getPrice());
                                            jsonArray.put(cartJsonObject);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    JSONObject userJsonObject = new JSONObject();
                                    try {
                                        userJsonObject.put("idorder",  "FS"+phone_user+randomNumber);
                                        userJsonObject.put("name", name_user);
                                        userJsonObject.put("phone",phone_user);
                                        userJsonObject.put("address", address_user);
                                        userJsonObject.put("total",txttotal.getText().toString());
                                        jsonArray.put(userJsonObject);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    params.put("json", jsonArray.toString());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }
                });
        }else {
                int totalItem=0;
                int totalquality=0;
                int delivery=0;

            adapter = new InfomationAdapter(CheckOutActivity.this, MainActivity_user.userCart);
            listView.setAdapter(adapter);
            for(int i=0;i<MainActivity_user.userCart.size();i++){
                totalItem += MainActivity_user.userCart.get(i).getPrice() * MainActivity_user.userCart.get(i).getQuantity();
                totalquality+= MainActivity_user.userCart.get(i).getQuantity();
            }
            txttotalitem.setText(""+ totalItem);
            delivery = (int) (totalItem * 0.1);
            txtDelyvery.setText(""+ delivery);
            total = totalItem - delivery;
            txttotal.setText(""+total);
            txtgiatri_check.setText(""+total);
            txttotalquality.setText("( "+totalquality+" Sản phẩm"+ " )");

            image_out_checkout.setOnClickListener(view -> onBackPressed());

            btndathang_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtaddress_check.getText().toString().trim().isEmpty()){
                            Toast.makeText(CheckOutActivity.this, "Bạn phải nhập địa chỉ", Toast.LENGTH_SHORT).show();
                        }else {
                           RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
                           StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanorders, new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                 if(response.equals("1")){
                                     Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                     MainActivity_user.userCart.clear();
                                     startActivity(new Intent(CheckOutActivity.this, OrderActivity.class));
                                 }else {
                                     Toast.makeText(CheckOutActivity.this, response, Toast.LENGTH_SHORT).show();
                                     Log.d("error_check", "onResponse: "+response);
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
                                   HashMap<String, String> params = new HashMap<>();
                                   JSONArray jsonArray = new JSONArray();
                                   Random random = new Random();
                                   int randomNumber = random.nextInt(90000) + 10000;
                                   for(int i=0;i<MainActivity_user.userCart.size();i++){
                                       JSONObject cartJsonObject = new JSONObject();
                                       try {
                                           Cart cart = MainActivity_user.userCart.get(i);
                                          cartJsonObject.put("cartid",cart.getProductId());
                                          cartJsonObject.put("cartname",cart.getProductName() );
                                          cartJsonObject.put("cartimage", cart.getProductImage());
                                          cartJsonObject.put("cartsize", cart.getSize());
                                          cartJsonObject.put("cartcolor", cart.getColor());
                                          cartJsonObject.put("cartquality",cart.getQuantity());
                                          cartJsonObject.put("cartprice",cart.getPrice());
                                          jsonArray.put(cartJsonObject);
                                       } catch (JSONException e) {
                                           throw new RuntimeException(e);
                                       }
                                   }
                                   JSONObject userJsonObject = new JSONObject();
                                   try {
                                       userJsonObject.put("idorder",  "FS"+phone_user+randomNumber);
                                       userJsonObject.put("name", name_user);
                                       userJsonObject.put("phone",phone_user);
                                       userJsonObject.put("address", address_user);
                                       userJsonObject.put("total",txttotal.getText().toString());
                                       jsonArray.put(userJsonObject);
                                   } catch (JSONException e) {
                                       throw new RuntimeException(e);
                                   }
                                   params.put("json", jsonArray.toString());
                                   return params;
                               }
                           };
                           requestQueue.add(stringRequest);
                        }
                    }
                });
        }

    }

    private void Anhxa(){
        listView = findViewById(R.id.listview_checkinfo);
        txttotalitem = findViewById(R.id.txtprice_check_info);
        txtDelyvery = findViewById(R.id.txtDelivery_check_info);
        txttotal = findViewById(R.id.txttotal_check_info);
        txttotalquality = findViewById(R.id.txttotalquality);
        txtgiatri_check = findViewById(R.id.txtgiatri_check);
        image_out_checkout = findViewById(R.id.image_out_checkout);
        txtname_check = findViewById(R.id.txtname_check);
        txtphone_check = findViewById(R.id.txtphone_check);
        txtaddress_check = findViewById(R.id.txtaddress_check);
        btnchangeaddress = findViewById(R.id.btnchangeaddress);
        btndathang_check = findViewById(R.id.btnDathang_check);
        arrayList = new ArrayList<>();
    }
}