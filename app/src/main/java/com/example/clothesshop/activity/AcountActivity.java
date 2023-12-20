package com.example.clothesshop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.clothesshop.fragment.LoginFragment;
import com.example.clothesshop.server.server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AcountActivity extends AppCompatActivity {
    Toolbar toolbar;
     TextView txtname_account,txtphone_account,txttotal_account,btnupdate,btnlogout;
     ImageView imageView;
     TextInputLayout tilname, tilpassword,tiladdress;
     TextInputEditText edname,edpassword,edaddress;
     int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);
        Anhxa();
        getUser();
        UpdateUser();
        ActionBar();
    }

    private void UpdateUser() {

        btnupdate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String nameupdate = edname.getText().toString();
              String passwordupdate = edpassword.getText().toString();
              String addressupdate = edaddress.getText().toString();
              RequestQueue requestQueue = Volley.newRequestQueue(AcountActivity.this);
              StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdateusers, new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                    if(response.equals("1")){
                        Toast.makeText(AcountActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                        edname.setText("");
                        edaddress.setText("");
                        edpassword.setText("");
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
                      params.put("id", String.valueOf(id));
                      params.put("name", nameupdate);
                      params.put("pass", passwordupdate);
                      params.put("address", addressupdate);
                      return params;
                  }
              };
          requestQueue.add(stringRequest);
          }
      });
        btnlogout.setOnClickListener(view -> {startActivity(new Intent(AcountActivity.this, LoginActivity.class));});
    }

    private void getUser() {
        String name="";
        String phone="";
        String img_user="";
        for(int i=0;i< LoginFragment.arrayList.size();i++){
             id = LoginFragment.arrayList.get(i).getId();
             name = LoginFragment.arrayList.get(i).getName();
             phone = String.valueOf(LoginFragment.arrayList.get(i).getPhone());
             img_user = LoginFragment.arrayList.get(i).getAvatar();
        }
        txtname_account.setText(""+ name);
        txtphone_account.setText("+84"+phone);
        Picasso.get().load(img_user).into(imageView);
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_account);
        txtname_account = findViewById(R.id.txtname_account);
        txtphone_account = findViewById(R.id.txtphone_account);
        txttotal_account = findViewById(R.id.txttotal_account);
        btnupdate = findViewById(R.id.btnupdate_account);
        btnlogout = findViewById(R.id.btnlogout_account);
        imageView = findViewById(R.id.image_account);
        tilname = findViewById(R.id.tilname_account);
        tilpassword = findViewById(R.id.tilpass_account);
        tiladdress = findViewById(R.id.tiladdress_account);
        edname = findViewById(R.id.edname_account);
        edpassword = findViewById(R.id.edpass_account);
        edaddress = findViewById(R.id.edaddress_account);
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