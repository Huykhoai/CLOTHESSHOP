package com.example.clothesshop.fragmentAdmin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.clothesshop.fragment.AllOrderFragment;
import com.example.clothesshop.fragment.LoginFragment;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.server.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeAdminFragment extends Fragment {
      EditText edpassword;
      TextView btn_changepass, txttotal_order,txttotal_product;
    public HomeAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);
        Anhxa(view);
        Changepassword();
        return view;
    }

    private void Changepassword() {
        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strpass = edpassword.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdateusers, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(getActivity(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
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
                        params.put("id", String.valueOf(LoginFragment.arrayList.get(0).getId()));
                        params.put("name", "");
                        params.put("pass", strpass);
                        params.put("address", "");
                        params.put("image", "");
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void Anhxa(View view) {
        edpassword = view.findViewById(R.id.edt_passnew);
        btn_changepass = view.findViewById(R.id.changePassword);
        txttotal_order = view.findViewById(R.id.waitOrder);
        txttotal_product = view.findViewById(R.id.total_product);
    }
}